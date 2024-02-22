package com.benyq.sodaworld.account.ui.widget

/**
 *
 * @author benyq
 * @date 2/20/2024
 *
 */
fun buildModels(): List<PanelButtonData> {
    return listOf(
        PanelButtonData("7", "7"),
        PanelButtonData("8", "8"),
        PanelButtonData("9", "9"),
        PanelButtonData("date", "今天"),
        PanelButtonData("4", "4"),
        PanelButtonData("5", "5"),
        PanelButtonData("6", "6"),
        PanelButtonData("add", "+"),
        PanelButtonData("1", "1"),
        PanelButtonData("2", "2"),
        PanelButtonData("3", "3"),
        PanelButtonData("minus", "-"),
        PanelButtonData("dot", "."),
        PanelButtonData("0", "0"),
        PanelButtonData("delete", "删除"),
        PanelButtonData("done", "完成"),
    )
}

data class PanelButtonData(val code: String, var text: String)