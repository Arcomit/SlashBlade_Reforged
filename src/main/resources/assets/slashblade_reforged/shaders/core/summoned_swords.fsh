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
uniform float DiscardThreshold;

uniform vec4 HDRColor;

uniform float CellDensity;
uniform float Spacing;
uniform float NoiseOffsetStrength;


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
    vec2 noiseOffset = (vec2(voronoi(uv, angleOffset, CellDensity * 2.0, Spacing),
                           voronoi(uv, angleOffset + 1.57, CellDensity * 2.0, Spacing)) - 0.5) * 2.0;
    vec4 sceneColor = texture(SamplerSceneColor, screenUv + noiseOffset * NoiseOffsetStrength * poweredVoronoi);

    // 给vertexColor上HDR
    vec4 VertexColorHDR = vertexColor * ColorModulator;
    VertexColorHDR.rgb *= HDRColor.a * HDRColor.rgb;

    // 计算屏幕颜色亮度
    float luminance = dot(sceneColor.rgb, vec3(0.2126, 0.7152, 0.0722));
    vec3 result;
    if (luminance < 0.2) {
        // 滤色混合 - 使暗部变亮
        result = 1.0 - (1.0 - sceneColor.rgb) * (1.0 - VertexColorHDR.rgb);
    } else {
        // 正片叠底混合 - 使亮部变暗
        result = sceneColor.rgb * VertexColorHDR.rgb;
    }

    //使用VertexColor的透明度
    vec4 blendColor = vec4(result, VertexColorHDR.a);

    vec4 compoundColor = blendColor;

    if (compoundColor.a < DiscardThreshold) discard;

    fragColor = linear_fog(compoundColor, vertexDistance, FogStart, FogEnd, FogColor);
}