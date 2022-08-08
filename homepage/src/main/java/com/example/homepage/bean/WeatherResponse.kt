package com.example.homepage.bean

data class WeatherResponse(
    val api_status: String,
    val api_version: String,
    val lang: String,
    val location: List<Double>,
    val result: Result,
    val server_time: Int,
    val status: String,
    val timezone: String,
    val tzshift: Int,
    val unit: String
)

data class Result(
    val alert: Alert,
    val daily: Daily,
    val forecast_keypoint: String,
    val hourly: Hourly,
    val minutely: Minutely,
    val primary: Int,
    val realtime: Realtime
)

data class Alert(
    val adcodes: List<Adcode>,
    val content: List<Content>,
    val status: String
)

data class Daily(
    val air_quality: AirQuality,
    val astro: List<Astro>,
    val cloudrate: List<Cloudrate>,
    val dswrf: List<Dswrf>,
    val humidity: List<Humidity>,
    val life_index: LifeIndex,
    val precipitation: List<Precipitation>,
    val precipitation_08h_20h: List<Precipitation>,
    val precipitation_20h_32h: List<Precipitation>,
    val pressure: List<Pressure>,
    val skycon: List<Skycon>,
    val skycon_08h_20h: List<Skycon>,
    val skycon_20h_32h: List<Skycon>,
    val status: String,
    val temperature: List<Temperature>,
    val temperature_08h_20h: List<Temperature>,
    val temperature_20h_32h: List<Temperature>,
    val visibility: List<Visibility>,
    val wind: List<Wind>,
    val wind_08h_20h: List<Wind>,
    val wind_20h_32h: List<Wind>
)

data class Hourly(
    val air_quality: AirQualityX,
    val apparent_temperature: List<ApparentTemperature>,
    val cloudrate: List<CloudrateX>,
    val description: String,
    val dswrf: List<DswrfX>,
    val humidity: List<HumidityX>,
    val precipitation: List<PrecipitationX>,
    val pressure: List<PressureX>,
    val skycon: List<SkyconX>,
    val status: String,
    val temperature: List<TemperatureX>,
    val visibility: List<VisibilityX>,
    val wind: List<WindX>
)

data class Minutely(
    val datasource: String,
    val description: String,
    val precipitation: List<Double>,
    val precipitation_2h: List<Double>,
    val probability: List<Double>,
    val status: String
)

data class Realtime(
    val air_quality: AirQualityXX,
    val apparent_temperature: Double,
    val cloudrate: Double,
    val dswrf: Double,
    val humidity: Double,
    val life_index: LifeIndexX,
    val precipitation: PrecipitationXX,
    val pressure: Double,
    val skycon: String,
    val status: String,
    val temperature: Double,
    val visibility: Double,
    val wind: WindXX
)

data class Adcode(
    val adcode: Int,
    val name: String
)

data class Content(
    val adcode: String,
    val alertId: String,
    val city: String,
    val code: String,
    val county: String,
    val description: String,
    val latlon: List<Double>,
    val location: String,
    val province: String,
    val pubtimestamp: Int,
    val regionId: String,
    val request_status: String,
    val source: String,
    val status: String,
    val title: String
)

data class AirQuality(
    val aqi: List<Aqi>,
    val pm25: List<Pm25>
)

data class Astro(
    val date: String,
    val sunrise: Sunrise,
    val sunset: Sunset
)

data class Cloudrate(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class Dswrf(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class Humidity(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class LifeIndex(
    val carWashing: List<CarWashing>,
    val coldRisk: List<ColdRisk>,
    val comfort: List<Comfort>,
    val dressing: List<Dressing>,
    val ultraviolet: List<Ultraviolet>
)

data class Precipitation(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double,
    val probability: Int
)

data class Pressure(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class Skycon(
    val date: String,
    val value: String
)

data class Temperature(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class Visibility(
    val avg: Double,
    val date: String,
    val max: Double,
    val min: Double
)

data class Wind(
    val avg: AvgX,
    val date: String,
    val max: MaxX,
    val min: MinX
)

data class Aqi(
    val avg: Avg,
    val date: String,
    val max: Max,
    val min: Min
)

data class Pm25(
    val avg: Int,
    val date: String,
    val max: Int,
    val min: Int
)

data class Avg(
    val chn: Int,
    val usa: Int
)

data class Max(
    val chn: Int,
    val usa: Int
)

data class Min(
    val chn: Int,
    val usa: Int
)

data class Sunrise(
    val time: String
)

data class Sunset(
    val time: String
)

data class CarWashing(
    val date: String,
    val desc: String,
    val index: String
)

data class ColdRisk(
    val date: String,
    val desc: String,
    val index: String
)

data class Comfort(
    val date: String,
    val desc: String,
    val index: String
)

data class Dressing(
    val date: String,
    val desc: String,
    val index: String
)

data class Ultraviolet(
    val date: String,
    val desc: String,
    val index: String
)

data class AvgX(
    val direction: Double,
    val speed: Double
)

data class MaxX(
    val direction: Double,
    val speed: Double
)

data class MinX(
    val direction: Double,
    val speed: Double
)

data class AirQualityX(
    val aqi: List<AqiX>,
    val pm25: List<Pm25X>
)

data class ApparentTemperature(
    val datetime: String,
    val value: Double
)

data class CloudrateX(
    val datetime: String,
    val value: Double
)

data class DswrfX(
    val datetime: String,
    val value: Double
)

data class HumidityX(
    val datetime: String,
    val value: Double
)

data class PrecipitationX(
    val datetime: String,
    val probability: Int,
    val value: Double
)

data class PressureX(
    val datetime: String,
    val value: Double
)

data class SkyconX(
    val datetime: String,
    val value: String
)

data class TemperatureX(
    val datetime: String,
    val value: Double
)

data class VisibilityX(
    val datetime: String,
    val value: Double
)

data class WindX(
    val datetime: String,
    val direction: Double,
    val speed: Double
)

data class AqiX(
    val datetime: String,
    val value: Value
)

data class Pm25X(
    val datetime: String,
    val value: Int
)

data class Value(
    val chn: Int,
    val usa: Int
)

data class AirQualityXX(
    val aqi: AqiXX,
    val co: Double,
    val description: Description,
    val no2: Int,
    val o3: Int,
    val pm10: Int,
    val pm25: Int,
    val so2: Int
)

data class LifeIndexX(
    val comfort: ComfortX,
    val ultraviolet: UltravioletX
)

data class PrecipitationXX(
    val local: Local,
    val nearest: Nearest
)

data class WindXX(
    val direction: Double,
    val speed: Double
)

data class AqiXX(
    val chn: Int,
    val usa: Int
)

data class Description(
    val chn: String,
    val usa: String
)

data class ComfortX(
    val desc: String,
    val index: Int
)

data class UltravioletX(
    val desc: String,
    val index: Double
)

data class Local(
    val datasource: String,
    val intensity: Double,
    val status: String
)

data class Nearest(
    val distance: Double,
    val intensity: Double,
    val status: String
)