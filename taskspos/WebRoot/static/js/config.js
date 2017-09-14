var config ={};
config.TASK_BRIDGEUSER = 1; //分析桥接用户
config.TASK_BRIDGECASE = 2;//分析桥接案例
config.TASK_SYNC = 3;//重新分析
config.TASK_WEIBO = 4;//抓取微博
config.TASK_REPOST_TREND = 5;//处理转发
config.TASK_STATUSES_COUNT = 6;//更新转发数、评论数
config.TASK_COMMENTS = 7;//抓取评论
config.TASK_IMPORTWEIBOURL = 8;//批量植入微博
config.TASK_IMPORTUSERID = 9;//批量植入用户
config.TASK_KEYWORD = 10;//抓取关键词
config.TASK_FRIEND = 11;//抓取关注
config.TASK_MIGRATEDATA = 12;//迁移数据
config.TASK_SNAPSHOT = 13;//更新快照
config.TASK_EVENTALERT = 14;//事件预警
config.TASK_WEBPAGE = 15;//抓取网页
config.TASK_REPOSTPATH = 16;//分析转发轨迹
config.TASK_COMMENTPATH = 17;//分析评论轨迹
config.TASK_COMMON = 20;//通用抓取

config.TASK_PAGESTYLE_ARTICLELIST = 1;//1.文章列表
config.TASK_PAGESTYLE_ARTICLEDETAIL = 2;//2.文章详情
config.TASK_PAGESTYLE_USERDETAIL = 3;//3.用户详情
config.TASK_PAGESTYLE_ALL = 4;//4.文章列表＋文章详情＋用户详情

config.allSource = [];//全部source
config.allAccountSource= [];//全部帐号source
config.allSpiderConfig = [];//全部爬虫配置


