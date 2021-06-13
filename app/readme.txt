程式架構:
MainActivity(on/off開關頁面)---
這邊會new一個全域setting_state class(這個class會記錄目前所有的設定，像是難易度、備用密碼、密碼、臉型的)，並且所有activity都可以讀去這個class(可透過呼叫MainActivity.app_state來取得)
                              |
                              |
                              |
                              menu_horizontal_scroll(水平滑動選單頁面，這頁會顯示休息時間/難易度等按鈕)
                              |                                                        |                                            |
                              |                                                        |                                            |
                              setting_resttime:設定休息時間介面                         setting_difficulity:設定難易度介面           剩下的還沒做完
