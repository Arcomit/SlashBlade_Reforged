#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
ResourceLocation索引生成器
根据目录结构生成层次索引和ResourceLocation字段
"""

import os
import re
from pathlib import Path
from typing import List, Dict, Set


class ResourceLocationGenerator:
    def __init__(self, base_path: str = "../src/main/resources/assets/slashblade_reforged", 
                 namespace: str = "slashblade_reforged"):
        self.base_path = Path(base_path)
        self.namespace = namespace
        self.class_hierarchy = {}
        
    def to_java_class_name(self, name: str) -> str:
        """将目录名转换为Java类名（首字母大写，驼峰命名）"""
        # 移除特殊字符，使用下划线分割
        clean_name = re.sub(r'[^a-zA-Z0-9_]', '_', name)
        parts = clean_name.split('_')
        return ''.join(word.capitalize() for word in parts if word)
    
    def to_java_field_name(self, filename: str) -> str:
        """将文件名转换为Java字段名（小驼峰命名法，去掉扩展名）"""
        name_without_ext = os.path.splitext(filename)[0]
        # 替换特殊字符为下划线，然后分割
        clean_name = re.sub(r'[^a-zA-Z0-9]', '_', name_without_ext)
        parts = clean_name.split('_')
        
        # 小驼峰命名：第一个单词小写，后续单词首字母大写
        if not parts:
            return 'unknown'
        
        result = parts[0].lower()
        for part in parts[1:]:
            if part:  # 跳过空字符串
                result += part.capitalize()
        
        return result
    
    def get_relative_path(self, file_path: Path) -> str:
        """获取相对于base_path的路径"""
        try:
            relative = file_path.relative_to(self.base_path)
            # 去掉文件扩展名，保留目录结构
            path_without_ext = str(relative.with_suffix(''))
            return path_without_ext.replace('\\', '/')  # 确保使用正斜杠
        except ValueError:
            return str(file_path).replace('\\', '/')
    
    def build_class_hierarchy(self, file_path: Path) -> str:
        """构建Java类层次结构"""
        try:
            relative = file_path.relative_to(self.base_path)
            parts = relative.parts[:-1]  # 去掉文件名，只保留目录
            
            hierarchy = ["R"]
            for part in parts:
                hierarchy.append(self.to_java_class_name(part))
            
            return '.'.join(hierarchy)
        except ValueError:
            return "R"
    
    def scan_directory(self, directory: Path = None) -> List[Dict]:
        """扫描目录并生成资源信息"""
        if directory is None:
            directory = self.base_path
            
        resources = []
        
        if not directory.exists():
            print(f"警告: 目录不存在: {directory}")
            return resources
        
        for root, dirs, files in os.walk(directory):
            root_path = Path(root)
            
            for file in files:
                file_path = root_path / file
                
                # 跳过隐藏文件和临时文件
                if file.startswith('.') or file.endswith('.tmp'):
                    continue
                
                relative_path = self.get_relative_path(file_path)
                class_hierarchy = self.build_class_hierarchy(file_path)
                field_name = self.to_java_field_name(file)
                
                # 生成两个字段：一个是基本字段，一个是带文件扩展名的字段
                resources.append({
                    'file_path': str(file_path),
                    'relative_path': relative_path,
                    'class_hierarchy': class_hierarchy,
                    'field_name': field_name,
                    'resource_location': f'ResourceLocation.fromNamespaceAndPath("{self.namespace}", "{relative_path}")',
                    'full_field_path': f'{class_hierarchy}.{field_name}'
                })
                
                # 如果有扩展名，生成带扩展名的字段
                if '.' in file:
                    ext = os.path.splitext(file)[1]
                    ext_clean = ext[1:]  # 去掉点号
                    field_name_with_ext = field_name + '$' + ext_clean  # $风格
                    relative_path_with_ext = str(file_path.relative_to(self.base_path)).replace('\\', '/')
                    
                    resources.append({
                        'file_path': str(file_path),
                        'relative_path': relative_path_with_ext,
                        'class_hierarchy': class_hierarchy,
                        'field_name': field_name_with_ext,
                        'resource_location': f'ResourceLocation.fromNamespaceAndPath("{self.namespace}", "{relative_path_with_ext}")',
                        'full_field_path': f'{class_hierarchy}.{field_name_with_ext}'
                    })
        
        return resources
    
    def generate_java_code(self, resources: List[Dict]) -> str:
        """生成Java代码"""
        if not resources:
            return "// 没有找到资源文件"
        
        java_code = []
        java_code.append("/**")
        java_code.append(" * 自动生成的ResourceLocation常量")
        java_code.append(" * 请勿手动修改此文件")
        java_code.append(" */")
        java_code.append("package mod.slashblade.reforged.utils.constant;")
        java_code.append("")
        java_code.append("import net.minecraft.resources.ResourceLocation;")
        java_code.append("")
        java_code.append("public class R {")
        java_code.append("")
        
        # 按照层次结构组织代码
        hierarchy_map = {}
        
        for resource in resources:
            hierarchy_parts = resource['class_hierarchy'].split('.')
            current_level = hierarchy_map
            
            for part in hierarchy_parts[1:]:  # 跳过'R'
                if part not in current_level:
                    current_level[part] = {'fields': [], 'children': {}}
                current_level = current_level[part]['children']
            
            # 添加字段到最后一级
            parent_level = hierarchy_map
            for part in hierarchy_parts[1:-1]:
                parent_level = parent_level[part]['children']
            
            if hierarchy_parts[-1] not in parent_level:
                parent_level[hierarchy_parts[-1]] = {'fields': [], 'children': {}}
            
            parent_level[hierarchy_parts[-1]]['fields'].append(resource)
        
        def generate_class_code(class_name: str, data: Dict, indent_level: int = 1) -> List[str]:
            indent = "    " * indent_level
            lines = []
            
            lines.append(f"{indent}public static class {class_name} {{")
            
            # 去重字段：确保同一个类中字段名唯一
            unique_fields = {}
            for resource in data['fields']:
                field_name = resource['field_name']
                if field_name not in unique_fields:
                    unique_fields[field_name] = resource
            
            # 生成字段
            for field_name, resource in unique_fields.items():
                field_declaration = f"public static final ResourceLocation {resource['field_name']} = {resource['resource_location']};"
                lines.append(f"{indent}    {field_declaration}")
            
            # 生成子类
            if data['fields'] and data['children']:
                lines.append("")  # 字段和子类之间加一个空行
            
            for i, (child_name, child_data) in enumerate(data['children'].items()):
                if child_data['fields'] or child_data['children']:
                    if i > 0:
                        lines.append("")  # 子类之间加一个空行
                    child_lines = generate_class_code(child_name, child_data, indent_level + 1)
                    lines.extend(child_lines)
            
            lines.append(f"{indent}}}")
            return lines
        
        # 直接生成R类的内容
        for i, (class_name, class_data) in enumerate(hierarchy_map.items()):
            if class_data['fields'] or class_data['children']:
                if i > 0:
                    java_code.append("")  # 顶级类之间加一个空行
                class_lines = generate_class_code(class_name, class_data, 1)
                java_code.extend(class_lines)
        
        java_code.append("}")
        
        return '\n'.join(java_code)
    
    def generate_summary(self, resources: List[Dict]) -> str:
        """生成总结信息"""
        if not resources:
            return "没有找到任何资源文件"
        
        summary = []
        summary.append(f"总共找到 {len(resources)} 个资源字段")
        summary.append("")
        
        # 按文件类型统计
        file_types = {}
        for resource in resources:
            if '$' in resource['field_name']:  # 带扩展名的字段
                continue
            ext = os.path.splitext(resource['relative_path'])[1]
            if ext:
                file_types[ext] = file_types.get(ext, 0) + 1
        
        summary.append("文件类型统计:")
        for ext, count in sorted(file_types.items()):
            summary.append(f"  {ext}: {count} 个文件")
        
        summary.append("")
        summary.append("示例字段:")
        for i, resource in enumerate(resources[:5]):
            if '$' not in resource['field_name']:  # 只显示基本字段
                summary.append(f"  {resource['full_field_path']} = {resource['resource_location']}")
        
        return '\n'.join(summary)


def main():
    """主函数"""
    print("ResourceLocation索引生成器")
    print("=" * 50)
    
    # 检查当前目录
    current_dir = Path.cwd()
    print(f"当前工作目录: {current_dir}")
    
    # 创建生成器实例
    generator = ResourceLocationGenerator()
    
    print(f"扫描目录: {generator.base_path}")
    
    # 扫描资源
    resources = generator.scan_directory()
    
    # 输出总结
    print(generator.generate_summary(resources))
    print()
    
    # 生成Java代码
    java_code = generator.generate_java_code(resources)
    
    # 保存Java文件到正确的位置
    output_dir = Path("../src/main/java/mod/slashblade/reforged/utils/constant")
    output_dir.mkdir(parents=True, exist_ok=True)
    output_file = output_dir / "R.java"
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(java_code)
    
    print(f"Java代码已保存到: {output_file}")
    
    # 显示部分代码预览
    print("\n代码预览:")
    print("-" * 50)
    lines = java_code.split('\n')
    for line in lines[:30]:  # 显示前30行
        print(line)
    
    if len(lines) > 30:
        print("...")
        print(f"(省略了 {len(lines) - 30} 行)")


if __name__ == "__main__":
    main()
