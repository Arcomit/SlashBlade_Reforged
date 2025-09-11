#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
OBJ模型缩放工具
将OBJ模型文件中的所有顶点坐标从原点进行等比缩放
"""

import os
import sys
import argparse
import shutil
from pathlib import Path
from typing import List, Tuple, Optional
import re


class ObjModelScaler:
    """OBJ模型缩放器"""
    
    def __init__(self, scale_factor: float, create_backup: bool = True):
        """
        初始化缩放器
        
        Args:
            scale_factor: 缩放倍数
            create_backup: 是否创建备份文件
        """
        if scale_factor <= 0:
            raise ValueError("缩放倍数必须大于0")
        
        self.scale_factor = scale_factor
        self.create_backup = create_backup
        self.processed_files = 0
        self.failed_files = 0
        self.errors = []
    
    def scale_vertex(self, line: str) -> str:
        """
        缩放顶点坐标
        
        Args:
            line: 包含顶点坐标的行 (格式: v x y z [w])
            
        Returns:
            缩放后的顶点行
        """
        parts = line.strip().split()
        if len(parts) < 4:  # v x y z 至少4个部分
            return line
        
        try:
            # 解析坐标
            x = float(parts[1]) * self.scale_factor
            y = float(parts[2]) * self.scale_factor
            z = float(parts[3]) * self.scale_factor
            
            # 保持6位小数精度
            scaled_line = f"v {x:.6f} {y:.6f} {z:.6f}"
            
            # 如果有第四个坐标(w)，也进行缩放
            if len(parts) > 4:
                try:
                    w = float(parts[4]) * self.scale_factor
                    scaled_line += f" {w:.6f}"
                except ValueError:
                    # 如果第四个参数不是数字，保持原样
                    scaled_line += f" {parts[4]}"
            
            # 保留行尾的任何注释
            if len(parts) > 5:
                scaled_line += " " + " ".join(parts[5:])
            
            return scaled_line + "\n"
            
        except ValueError as e:
            # 如果解析失败，返回原行
            print(f"警告: 无法解析顶点坐标: {line.strip()} - {e}")
            return line
    
    def process_obj_file(self, input_file: Path, output_file: Optional[Path] = None) -> bool:
        """
        处理单个OBJ文件
        
        Args:
            input_file: 输入文件路径
            output_file: 输出文件路径，如果为None则覆盖原文件
            
        Returns:
            处理是否成功
        """
        if not input_file.exists():
            error_msg = f"文件不存在: {input_file}"
            self.errors.append(error_msg)
            print(f"错误: {error_msg}")
            return False
        
        if not input_file.suffix.lower() == '.obj':
            error_msg = f"文件不是OBJ格式: {input_file}"
            self.errors.append(error_msg)
            print(f"跳过: {error_msg}")
            return False
        
        try:
            # 读取原文件
            with open(input_file, 'r', encoding='utf-8', errors='ignore') as f:
                lines = f.readlines()
            
            # 创建备份
            if self.create_backup and output_file is None:
                backup_file = input_file.with_suffix(input_file.suffix + '.backup')
                shutil.copy2(input_file, backup_file)
                print(f"创建备份: {backup_file}")
            
            # 处理每一行
            processed_lines = []
            vertex_count = 0
            
            for line in lines:
                stripped_line = line.strip()
                
                # 检查是否是顶点行
                if stripped_line.startswith('v ') and not stripped_line.startswith('vt ') and not stripped_line.startswith('vn '):
                    # 这是顶点坐标行
                    processed_lines.append(self.scale_vertex(line))
                    vertex_count += 1
                else:
                    # 保持其他行不变
                    processed_lines.append(line)
            
            # 确定输出文件路径
            if output_file is None:
                output_file = input_file
            
            # 写入处理后的文件
            with open(output_file, 'w', encoding='utf-8') as f:
                f.writelines(processed_lines)
            
            print(f"✅ 处理完成: {input_file}")
            print(f"   -> 输出: {output_file}")
            print(f"   -> 缩放了 {vertex_count} 个顶点")
            print(f"   -> 缩放倍数: {self.scale_factor}")
            
            self.processed_files += 1
            return True
            
        except Exception as e:
            error_msg = f"处理文件时发生错误 {input_file}: {e}"
            self.errors.append(error_msg)
            print(f"❌ 错误: {error_msg}")
            self.failed_files += 1
            return False
    
    def process_directory(self, input_dir: Path, output_dir: Optional[Path] = None, 
                         recursive: bool = False) -> None:
        """
        批量处理目录中的OBJ文件
        
        Args:
            input_dir: 输入目录
            output_dir: 输出目录，如果为None则覆盖原文件
            recursive: 是否递归处理子目录
        """
        if not input_dir.exists():
            error_msg = f"目录不存在: {input_dir}"
            self.errors.append(error_msg)
            print(f"错误: {error_msg}")
            return
        
        if not input_dir.is_dir():
            error_msg = f"路径不是目录: {input_dir}"
            self.errors.append(error_msg)
            print(f"错误: {error_msg}")
            return
        
        print(f"扫描目录: {input_dir}")
        print(f"递归模式: {'是' if recursive else '否'}")
        
        # 查找所有OBJ文件
        pattern = "**/*.obj" if recursive else "*.obj"
        obj_files = list(input_dir.glob(pattern))
        
        if not obj_files:
            print("未找到任何OBJ文件")
            return
        
        print(f"找到 {len(obj_files)} 个OBJ文件")
        print("-" * 50)
        
        # 处理每个文件
        for obj_file in obj_files:
            if output_dir:
                # 计算相对路径并创建输出路径
                relative_path = obj_file.relative_to(input_dir)
                output_file = output_dir / relative_path
                
                # 确保输出目录存在
                output_file.parent.mkdir(parents=True, exist_ok=True)
            else:
                output_file = None
            
            self.process_obj_file(obj_file, output_file)
            print()
    
    def print_summary(self) -> None:
        """打印处理总结"""
        print("=" * 60)
        print("处理总结")
        print("=" * 60)
        print(f"✅ 成功处理: {self.processed_files} 个文件")
        print(f"❌ 处理失败: {self.failed_files} 个文件")
        print(f"🔧 缩放倍数: {self.scale_factor}")
        print(f"💾 创建备份: {'是' if self.create_backup else '否'}")
        
        if self.errors:
            print(f"\n错误详情:")
            for i, error in enumerate(self.errors, 1):
                print(f"  {i}. {error}")
        
        if self.processed_files > 0:
            print(f"\n🎉 处理完成！")
        else:
            print(f"\n⚠️  没有成功处理任何文件")


def main():
    """主函数"""
    parser = argparse.ArgumentParser(
        description="OBJ模型缩放工具 - 将OBJ模型文件中的所有顶点坐标从原点进行等比缩放",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
示例用法:
  # 将单个文件放大2倍
  python scale_obj_model.py model.obj --scale 2.0
  
  # 缩放后输出到新文件
  python scale_obj_model.py model.obj --scale 0.5 --output model_small.obj
  
  # 批量处理目录
  python scale_obj_model.py models/ --scale 1.5 --recursive
  
  # 批量处理并输出到指定目录
  python scale_obj_model.py models/ --scale 2.0 --output-dir scaled/ --recursive

注意:
  - 缩放倍数 > 1.0 表示放大，< 1.0 表示缩小
  - 默认会创建 .backup 备份文件
  - 只处理顶点坐标(v)，纹理坐标(vt)和法向量(vn)保持不变
        """
    )
    
    parser.add_argument(
        'input_path',
        help='输入文件或目录路径'
    )
    
    parser.add_argument(
        '--scale', '-s',
        type=float,
        required=True,
        help='缩放倍数 (例如: 2.0=放大2倍, 0.5=缩小到一半)'
    )
    
    parser.add_argument(
        '--output', '-o',
        help='输出文件路径 (仅单文件模式有效)'
    )
    
    parser.add_argument(
        '--output-dir', '-d',
        help='输出目录路径 (批量处理模式)'
    )
    
    parser.add_argument(
        '--recursive', '-r',
        action='store_true',
        help='递归处理子目录中的OBJ文件'
    )
    
    parser.add_argument(
        '--no-backup',
        action='store_true',
        help='不创建备份文件'
    )
    
    args = parser.parse_args()
    
    # 验证输入
    input_path = Path(args.input_path)
    
    if args.scale <= 0:
        print("错误: 缩放倍数必须大于0")
        sys.exit(1)
    
    # 创建缩放器
    scaler = ObjModelScaler(
        scale_factor=args.scale,
        create_backup=not args.no_backup
    )
    
    print("OBJ模型缩放工具")
    print("=" * 60)
    print(f"输入路径: {input_path}")
    print(f"缩放倍数: {args.scale}")
    
    try:
        if input_path.is_file():
            # 单文件模式
            if args.output_dir:
                print("警告: 单文件模式下忽略 --output-dir 参数")
            
            output_file = Path(args.output) if args.output else None
            if output_file:
                print(f"输出文件: {output_file}")
            
            print("-" * 60)
            scaler.process_obj_file(input_path, output_file)
            
        elif input_path.is_dir():
            # 目录模式
            if args.output:
                print("警告: 目录模式下忽略 --output 参数")
            
            output_dir = Path(args.output_dir) if args.output_dir else None
            if output_dir:
                print(f"输出目录: {output_dir}")
                output_dir.mkdir(parents=True, exist_ok=True)
            
            print("-" * 60)
            scaler.process_directory(input_path, output_dir, args.recursive)
            
        else:
            print(f"错误: 路径不存在或不是文件/目录: {input_path}")
            sys.exit(1)
        
        # 打印总结
        scaler.print_summary()
        
    except KeyboardInterrupt:
        print("\n\n用户中断操作")
        sys.exit(1)
    except Exception as e:
        print(f"\n程序发生未预期的错误: {e}")
        sys.exit(1)


if __name__ == "__main__":
    main()
