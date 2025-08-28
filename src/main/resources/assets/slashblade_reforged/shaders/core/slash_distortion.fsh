#version 150
#moj_import <fog.glsl>
#moj_import <photon:particle_utils.glsl>

uniform sampler2D SamplerSceneColor;
uniform sampler2D SamplerCurve;

uniform vec2 ScreenSize;
uniform float GameTime;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform vec4 HDRColor;

uniform float CellDensity;
uniform float Spacing;

uniform float NoiseOffsetStrength;
uniform float DispersionStrength;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;

in float pT;

out vec4 fragColor;

// 生成随机2D向量
vec2 random2(vec2 p) {
    float x = dot(p, vec2(6533.4, 244.5));
    float y = dot(p, vec2(3155.6, 326.7));
    vec2 noise = vec2(x, y);
    noise = sin(noise);
    noise = noise * 33758.5453;
    noise = fract(noise);
    return noise;
}

// Voronoi 噪声函数
float voronoi(vec2 uv, float angleOffset, float cellDensity, float spacing) {
    uv *= cellDensity;

    vec2 iuv = floor(uv);
    vec2 fuv = fract(uv);

    float minDist = 1.0;

    for (int y = -1; y <= 1; y++) {
        for (int x = -1; x <= 1; x++) {
            vec2 neighbor = vec2(float(x), float(y));
            vec2 point = random2(iuv + neighbor);
            point = 0.5 + 0.5 * sin(angleOffset + 6.2831 * point);
            vec2 diff = neighbor + point - fuv;
            float dist = length(diff) * spacing;
            minDist = min(minDist, dist);
        }
    }

    return minDist;
}

void main() {
    vec2 uv = texCoord0.xy;
    vec2 screenUv = gl_FragCoord.xy / ScreenSize;

    // 输入参数 - 使用时间变量使角度偏移随时间变化
    float iTime = GameTime * 1000.;
    float angleOffset = iTime * 3.0; // 随时间变化的角度偏移

    // Voronoi噪声
    float VoronoiNoise = voronoi(uv, angleOffset, CellDensity, Spacing);
    // 溶解强度
    float DissolutionStrength = 0.0 + (getCurveValue(SamplerCurve, 0, pT) * 24.5);
    // 溶解后的Voronoi噪声
    float poweredVoronoi = pow(VoronoiNoise, DissolutionStrength);

    // 径向渐变
    vec2 delta = uv - vec2(0.5, 0.5);
    float RadialGradient = length(delta) * 2.0;
    RadialGradient = 1.0 - RadialGradient;
    RadialGradient = clamp(RadialGradient,0.0,1.0);
    RadialGradient = pow(RadialGradient , 2.85);

    float color = poweredVoronoi * RadialGradient;

    // 使用噪声值对屏幕坐标进行偏移
    vec2 noiseOffset = (vec2(voronoi(uv, angleOffset, CellDensity * 2.0, Spacing),
                           voronoi(uv, angleOffset + 1.57, CellDensity * 2.0, Spacing)) - 0.5) * 2.0;

    // 色散效果参数
    vec2 dispersionOffset = noiseOffset * NoiseOffsetStrength * color;

    // 对RGB通道分别应用不同的偏移量来创建色散效果
    vec2 redOffset = dispersionOffset * (1.0 + DispersionStrength);
    vec2 greenOffset = dispersionOffset;
    vec2 blueOffset = dispersionOffset * (1.0 - DispersionStrength);

    // 分别采样RGB通道
    float r = texture(SamplerSceneColor, screenUv + redOffset).r;

    // 不喜欢偏移绿色，B格不够
    float g = texture(SamplerSceneColor, screenUv + greenOffset).g;
    //float g = texture(SamplerSceneColor, screenUv).g;
    float b = texture(SamplerSceneColor, screenUv + blueOffset).b;

    // 组合RGB通道
    vec4 sceneColor = vec4(r, g, b, 1.0);

    fragColor = linear_fog(sceneColor, vertexDistance, FogStart, FogEnd, FogColor);
}