package com.example.homepage.bean

data class CovidResponse(
    val `data`: CovidData,
    val info: String,
    val ret: Int
)

data class CovidData(
    val diseaseh5Shelf: Diseaseh5Shelf,
    val statisGradeCityDetail: List<StatisGradeCityDetail>
)

data class Diseaseh5Shelf(
    val areaTree: List<AreaTree>,
    val chinaAdd: ChinaAdd,
    val chinaTotal: ChinaTotal,
    val isShowAdd: Boolean,
    val lastUpdateTime: String,
    val showAddSwitch: ShowAddSwitch
)

data class StatisGradeCityDetail(
    val city: String,
    val confirm: Int,
    val confirmAdd: Int,
    val date: String,
    val dead: Int,
    val grade: String,
    val heal: Int,
    val mtime: String,
    val nowConfirm: Int,
    val province: String,
    val sdate: String,
    val syear: Int,
    val wzz_add: String
)

data class AreaTree(
    val children: List<Children>,
    val name: String,
    val today: TodayXX,
    val total: TotalXX
)

data class ChinaAdd(
    val confirm: Int,
    val dead: Int,
    val heal: Int,
    val importedCase: Int,
    val localConfirm: Int,
    val localConfirmH5: Int,
    val noInfect: Int,
    val noInfectH5: Int,
    val nowConfirm: Int,
    val nowSevere: Int,
    val suspect: Int
)

data class ChinaTotal(
    val confirm: Int,
    val confirmAdd: Int,
    val dead: Int,
    val deadAdd: Int,
    val heal: Int,
    val highRiskAreaNum: Int,
    val importedCase: Int,
    val localConfirm: Int,
    val localConfirmAdd: Int,
    val localConfirmH5: Int,
    val localWzzAdd: Int,
    val local_acc_confirm: Int,
    val mRiskTime: String,
    val mediumRiskAreaNum: Int,
    val mtime: String,
    val noInfect: Int,
    val noInfectH5: Int,
    val nowConfirm: Int,
    val nowLocalWzz: Int,
    val nowSevere: Int,
    val showLocalConfirm: Int,
    val showlocalinfeciton: Int,
    val suspect: Int
)

data class ShowAddSwitch(
    val all: Boolean,
    val confirm: Boolean,
    val dead: Boolean,
    val heal: Boolean,
    val importedCase: Boolean,
    val localConfirm: Boolean,
    val localinfeciton: Boolean,
    val noInfect: Boolean,
    val nowConfirm: Boolean,
    val nowSevere: Boolean,
    val suspect: Boolean
)

data class Children(
    val adcode: String,
    val children: List<ChildrenX>,
    val date: String,
    val name: String,
    val today: TodayX,
    val total: TotalX
)

data class TodayXX(
    val confirm: Int,
    val isUpdated: Boolean
)

data class TotalXX(
    val adcode: String,
    val confirm: Int,
    val continueDayZeroLocalConfirm: Int,
    val continueDayZeroLocalConfirmAdd: Int,
    val dead: Int,
    val heal: Int,
    val highRiskAreaNum: Int,
    val mediumRiskAreaNum: Int,
    val mtime: String,
    val nowConfirm: Int,
    val provinceLocalConfirm: Int,
    val showHeal: Boolean,
    val showRate: Boolean,
    val wzz: Int
)

data class ChildrenX(
    val adcode: String,
    val date: String,
    val name: String,
    val today: Today,
    val total: Total
)

data class TodayX(
    val abroad_confirm_add: Int,
    val confirm: Int,
    val confirmCuts: Int,
    val dead_add: Int,
    val isUpdated: Boolean,
    val local_confirm_add: Int,
    val tip: String,
    val wzz_add: Int
)

data class TotalX(
    val adcode: String,
    val confirm: Int,
    val continueDayZeroConfirm: Int,
    val continueDayZeroConfirmAdd: Int,
    val continueDayZeroLocalConfirmAdd: Int,
    val dead: Int,
    val heal: Int,
    val highRiskAreaNum: Int,
    val mediumRiskAreaNum: Int,
    val mtime: String,
    val nowConfirm: Int,
    val provinceLocalConfirm: Int,
    val showHeal: Boolean,
    val showRate: Boolean,
    val wzz: Int
)

data class Today(
    val confirm: Int,
    val confirmCuts: Int,
    val isUpdated: Boolean,
    val local_confirm_add: Int,
    val wzz_add: String
)

data class Total(
    val adcode: String,
    val confirm: Int,
    val continueDayZeroLocalConfirm: Int,
    val continueDayZeroLocalConfirmAdd: Int,
    val dead: Int,
    val grade: String,
    val heal: Int,
    val highRiskAreaNum: Int,
    val mediumRiskAreaNum: Int,
    val mtime: String,
    val nowConfirm: Int,
    val provinceLocalConfirm: Int,
    val showHeal: Boolean,
    val showRate: Boolean,
    val wzz: Int
)