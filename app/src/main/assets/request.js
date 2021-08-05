const bkCityStr = {
    "北京": "bj",
    "上海": "sh",
    "广州": "gz",
    "深圳": "sz",
    "成都": "cd",
    "合肥": "hf",
    "芜湖": "wuhu",
    "淮南": "huainan",
    "马鞍山": "mas",
    "铜陵": "tl",
    "安庆": "aq",
    "黄山": "huangshan",
    "阜阳": "fy",
    "六安": "la",
    "亳州": "bozhou",
    "池州": "chizhou",
    "宣城": "xuancheng",
    "滁州": "cz",
    "重庆": "cq",
    "福州": "fz",
    "厦门": "xm",
    "莆田": "pt",
    "三明": "sm",
    "泉州": "quanzhou",
    "宁德": "nd",
    "龙岩": "ly",
    "漳州": "zhangzhou",
    "珠海": "zh",
    "佛山": "fs",
    "江门": "jiangmen",
    "湛江": "zhanjiang",
    "肇庆": "zq",
    "惠州": "hui",
    "河源": "heyuan",
    "东莞": "dg",
    "中山": "zs",
    "清远": "qy",
    "南宁": "nn",
    "柳州": "liuzhou",
    "桂林": "gl",
    "北海": "bh",
    "防城港": "fcg",
    "崇左": "chongzuo",
    "贵阳": "gy",
    "六盘水": "lps",
    "黔南": "qn",
    "遵义": "zunyi",
    "黔西南": "qianxinan",
    "兰州": "lz",
    "天水": "tianshui",
    "庆阳": "qingyang",
    "石家庄": "sjz",
    "唐山": "ts",
    "邯郸": "hd",
    "邢台": "xt",
    "保定": "bd",
    "沧州": "cangzhou",
    "廊坊": "lf",
    "衡水": "hs",
    "承德": "chengde",
    "秦皇岛": "qhd",
    "张家口": "zjk",
    "哈尔滨": "hrb",
    "伊春": "yichun",
    "郑州": "zz",
    "开封": "kf",
    "洛阳": "luoyang",
    "平顶山": "pds",
    "安阳": "ay",
    "鹤壁": "hb",
    "新乡": "xinxiang",
    "焦作": "jiaozuo",
    "濮阳": "py",
    "许昌": "xc",
    "漯河": "luohe",
    "南阳": "ny",
    "商丘": "shangqiu",
    "信阳": "xinyang",
    "周口": "zk",
    "驻马店": "zmd",
    "三门峡": "smx",
    "武汉": "wh",
    "黄石": "huangshi",
    "宜昌": "yichang",
    "襄阳": "xy",
    "鄂州": "ez",
    "荆门": "jm",
    "孝感": "xg",
    "荆州": "jingzhou",
    "仙桃": "xiantao",
    "潜江": "qianjiang",
    "黄冈": "hg",
    "咸宁": "xn",
    "随州": "suizhou",
    "长沙": "cs",
    "株洲": "zhuzhou",
    "湘潭": "xiangtan",
    "衡阳": "hy",
    "邵阳": "shaoyang",
    "岳阳": "yy",
    "常德": "changde",
    "郴州": "chenzhou",
    "永州": "yongzhou",
    "怀化": "hh",
    "湘西土家族苗族自治州": "xx",
    "浏阳": "liuyang",
    "宁乡": "ningxiang",
    "海口": "hk",
    "三亚": "san",
    "五指山": "wzs",
    "琼海": "qh",
    "儋州": "dz",
    "文昌": "wc",
    "万宁": "wn",
    "东方": "dongfang",
    "定安": "da",
    "澄迈": "cm",
    "临高": "lg",
    "乐东": "ld",
    "陵水": "ls",
    "保亭": "bt",
    "琼中": "qz",
    "长春": "cc",
    "吉林": "jl",
    "四平": "sp",
    "通化": "tonghua",
    "南京": "nj",
    "无锡": "wx",
    "徐州": "xz",
    "常州": "changzhou",
    "苏州": "su",
    "常熟": "changshu",
    "昆山": "ks",
    "南通": "nt",
    "连云港": "lyg",
    "淮安": "ha",
    "盐城": "yc",
    "扬州": "yz",
    "镇江": "zj",
    "泰州": "tz",
    "丹阳": "danyang",
    "海门": "haimen",
    "句容": "jr",
    "启东": "qidong",
    "如皋": "rg",
    "江阴": "jy",
    "海安": "haian",
    "太仓": "taicang",
    "南昌": "nc",
    "景德镇": "jdz",
    "萍乡": "pingxiang",
    "九江": "jiujiang",
    "新余": "xinyu",
    "赣州": "ganzhou",
    "吉安": "jian",
    "宜春": "ych",
    "抚州": "fuzhou",
    "上饶": "sr",
    "沈阳": "sy",
    "大连": "dl",
    "鞍山": "as",
    "抚顺": "fushun",
    "本溪": "bx",
    "丹东": "dd",
    "营口": "yk",
    "盘锦": "pj",
    "铁岭": "tieling",
    "呼和浩特": "hhht",
    "包头": "baotou",
    "赤峰": "cf",
    "通辽": "tongliao",
    "鄂尔多斯": "eeds",
    "乌兰察布": "wlcb",
    "银川": "yinchuan",
    "西宁": "xining",
    "太原": "ty",
    "大同": "dt",
    "运城": "yuncheng",
    "临汾": "linfen",
    "晋中": "jz",
    "济南": "jn",
    "青岛": "qd",
    "淄博": "zb",
    "枣庄": "zaozhuang",
    "东营": "dongying",
    "烟台": "yt",
    "潍坊": "wf",
    "济宁": "jining",
    "泰安": "ta",
    "威海": "weihai",
    "莱芜": "lw",
    "临沂": "linyi",
    "德州": "dezhou",
    "滨州": "binzhou",
    "菏泽": "heze",
    "自贡": "zg",
    "攀枝花": "pzh",
    "泸州": "luzhou",
    "德阳": "dy",
    "绵阳": "mianyang",
    "遂宁": "sn",
    "内江": "neijiang",
    "南充": "nanchong",
    "眉山": "ms",
    "宜宾": "yibin",
    "广安": "ga",
    "达州": "dazhou",
    "雅安": "yaan",
    "巴中": "bz",
    "资阳": "ziyang",
    "凉山": "liangshan",
    "乐山": "leshan",
    "广元": "guangyuan",
    "西安": "xa",
    "宝鸡": "baoji",
    "咸阳": "xianyang",
    "渭南": "weinan",
    "延安": "ya",
    "汉中": "hanzhong",
    "安康": "ak",
    "天津": "tj",
    "拉萨": "lasa",
    "乌鲁木齐": "wlmq",
    "昆明": "km",
    "曲靖": "qj",
    "玉溪": "yx",
    "保山": "baoshan",
    "丽江": "lj",
    "西双版纳傣族自治州": "xsbn",
    "大理": "dali",
    "杭州": "hz",
    "宁波": "nb",
    "温州": "wz",
    "嘉兴": "jx",
    "湖州": "huzhou",
    "绍兴": "sx",
    "金华": "jh",
    "义乌": "yw",
    "衢州": "quzhou",
    "舟山": "zhoushan",
    "台州": "taizhou"
};

const djlCityStr = {
    "重庆": "cq",
    "福州": "fz",
    "成都": "cd"
}

// 数据来源(bk/djl)  城市 类型(二手还是新房xf/es) 用户信息 页码
function getUrl(obj) {
    let src = obj["src"];
    let city = obj["city"];
    let _type = obj["type"];
    let userInfo = obj["userInfo"];
    let page = obj["page"]
    if (src === "bk") {
        return buildBeiKeDomain(city, _type) + buildBeiKeAreaParam(userInfo["builtArea"], _type) + buildBeiKePriceParam(userInfo["budget"]) + buildBeiKeHouseTypeParam(userInfo["houseType"]) +buildBeiKePageParam(page)+ buildBeiKeLocationParam(userInfo["area"])
    } else if (src === "djl") {
        return buildDjlDomain(city, _type) + buildDjlAreaParam(userInfo["builtArea"], _type) + buildDjlPriceParam(userInfo["budget"]) + buildBeiKeHouseTypeParam(userInfo["houseType"]) + buildBeiKePageParam(page) + buildBeiKeLocationParam(userInfo["area"])
    }
}

function buildBeiKeDomain(city, _type) {
    if (_type === "xf") {
        return "https://" + bkCityStr[city] + ".fang.ke.com/loupan/";
    } else if (_type === "es") {
        return "https://" + bkCityStr[city] + ".ke.com/ershoufang/";
    } else {
        return "";
    }
}

function buildDjlDomain(city, _type) {
    if (_type === "xf") {
        return "https://" + djlCityStr[city] + ".daojiale.com/xinfang/";
    } else if (_type === "es") {
        return "https://" + djlCityStr[city] + ".daojiale.com/ershoufang/";
    } else {
        return "";
    }
}

function buildBeiKeAreaParam(area, _type) {
    if (area === "0") {
        return "";
    }
    if (_type === "xf") {
        return "nha" + String(Number(area) - 1);
    } else if (_type === "es") {
        return "a" + String(area);
    } else {
        return "";
    }
}

function buildDjlAreaParam(area, _type) {
    switch (area) {
        case "1":
            return "bba0eba30";
        case "2":
            return "bba30eba50";
        case "3":
            return "bba50eba70";
        case "4":
            return "bba70eba90";
        case "5":
            return "bba90eba120";
        case "6":
            return "bba120eba150";
        case "7":
            return "bba150eba200";
        case "8":
            return "bba200eba300";
        case "9":
            return "bba300eba5000";
    }
}

function buildBeiKePriceParam(price) {
    return "p" + String(price);
}

function buildDjlPriceParam(price) {
    switch (price) {
        case "1":
            return "bp0ep40";
        case "2":
            return "bp40ep60";
        case "3":
            return "bp600ep80";
        case "4":
            return "bp80ep100";
        case "5":
            return "bp100ep150";
        case "6":
            return "bp150ep200";
        case "7":
            return "bp200ep50000";
        case "8":
            return "bp200ep50000";
    }
}

function buildBeiKeHouseTypeParam(houseTypes) {
    let s = houseTypes.split(",");
    let p = "";
    for (let i = 0; i < s.length; i++) {
        if (s[i] !== "") {
            p = p + "l" + s[i];
        }
    }
    return p;
}

function buildBeiKePageParam(page) {
    return "pg" + String(page);
}

function buildBeiKeLocationParam(location) {
    return "rs" + String(location);
}

function getEleWithDefault(func_op, _default) {
    try {
        return func_op();
    } catch (e) {
        console.log(e);
        return _default
    }
}

// 城市 类型(二手还是新房) 数据打包成对象
function parseStringAsHtml(text) {
    let _type = text["type"];
    let src = text["src"]
    // 将string转为html对象
    let parser = new DOMParser();
    let dom = parser.parseFromString(text["data"], "text/html");
    if (_type === "xf") {
        switch (src) {
            case "bk":
                return parseBeiKeXinFang(dom, text["city"]);
            case "djl":
                return parseDjlXinFang(dom);
        }
    } else if (_type === "es") {
        switch (src) {
            case "bk":
                return parseBeiKeErShou(dom);
            case "djl":
                return parseDjlErShou(dom);
        }
    } else {
        return [];
    }
}

function parseBeiKeXinFang(dom, city) {
    let resultCount = getEleWithDefault(function () {
        return Number(dom.getElementsByClassName("resblock-have-find")[0].getElementsByClassName("value")[0].textContent)
    }, 0)
    // 解析单个房源信息
    let lis = getEleWithDefault(function () {
        return dom.getElementsByClassName("resblock-list post_ulog_exposure_scroll has-results");
    }, undefined);
    let houses = [];
    if (lis === undefined || resultCount === 0) {
        return houses;
    }
    let domain = "https://" + bkCityStr[city] + ".fang.ke.com";
    for (let i = 0; i < lis.length; i++) {
        let li = lis[i];
        let imgA = getEleWithDefault(function () {
            return li.getElementsByClassName("resblock-img-wrapper")[0];
        }, undefined);
        // 详情地址
        let detailUrl = getEleWithDefault(function () {
            return domain + imgA.attributes["href"].nodeValue;
        }, "");
        // 楼盘名称
        let title = getEleWithDefault(function () {
            return imgA.attributes["title"].nodeValue;
        }, "");
        // 图片地址
        let imgUrl = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["data-original"].nodeValue;
        }, "");
        // 楼盘地址
        let location = getEleWithDefault(function () {
            return li.getElementsByClassName("resblock-location")[0].textContent;
        }, "");
        // 楼盘户型 房屋面积
        let houseArea = "";
        let houseType = "";
        getEleWithDefault(function () {
            let houseA = li.getElementsByClassName("resblock-room")[0];
            let spans = houseA.getElementsByTagName("span");
            for (let j = 0; j < spans.length; j++) {
                let span = spans[j];
                if (span.className === "area") {
                    houseArea = span.textContent
                } else {
                    houseType = houseType + span.textContent;
                }
            }
        }, "");
        // 楼盘单价
        let priceStr = getEleWithDefault(function () {
            return li.getElementsByClassName("number")[0].textContent;
        }, "");
        let priceDesc = getEleWithDefault(function () {
            return li.getElementsByClassName("desc")[0].textContent;
        }, "")
        let priceSecond = getEleWithDefault(function () {
            return li.getElementsByClassName("second")[0].textContent;
        }, "");
        let house = {
            detailUrl: detailUrl,
            name: title,
            imgUrl: imgUrl,
            location: location,
            area: houseArea,
            priceFirst: priceStr + priceDesc,
            priceSecond: priceSecond,
            type: houseType
        };
        console.log(house.detailUrl, house.name, house.location, house.imgUrl, house.area, house.priceFirst, house.priceSecond, house.type);
        houses.push(house);
    }
    return {"houses": houses, "total": resultCount};
}

function parseBeiKeErShou(dom) {
    let resultCount = getEleWithDefault(function () {
        return Number(dom.getElementsByClassName("total fl")[0].getElementsByTagName("span")[0].textContent)
    }, 0)
    let lis = getEleWithDefault(function () {
        return dom.getElementsByClassName("clear");
    }, undefined);
    let houses = [];
    if (lis === undefined || resultCount === 0) {
        return houses;
    }
    for (let i = 0; i < lis.length; i++) {
        let li = lis[i];
        if (li.tagName !== "LI") {
            continue;
        }
        let imgA = getEleWithDefault(function () {
            return li.getElementsByClassName("img")[0];
        }, undefined);
        let detailUrl = getEleWithDefault(function () {
            return imgA.attributes["href"].nodeValue;
        }, "");
        let imgUrl = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["data-original"].nodeValue;
        }, "");
        let title = getEleWithDefault(function () {
            return imgA.attributes["title"].nodeValue;
        }, "");
        let location = getEleWithDefault(function () {
            return li.getElementsByClassName("positionInfo")[0].getElementsByTagName("a")[0].textContent;
        }, "");
        let houseLevel = "";
        let houseYear = "";
        let houseArea = "";
        let houseDirection = "";
        let houseType = "";
        getEleWithDefault(function () {
            let info = li.getElementsByClassName("houseInfo")[0].textContent;
            let info_list = info.replace(/\s+/g, "").split("|");
            for (let j = 0; j < info_list.length; j++) {
                if (info_list[j].includes("楼层")) {
                    houseLevel = info_list[j];
                } else if (info_list[j].includes("年建")) {
                    houseYear = info_list[j];
                } else if (info_list[j].includes("平米")) {
                    houseArea = info_list[j];
                } else if (info_list[j].includes("室")) {
                    houseType = info_list[j];
                } else {
                    houseDirection = info_list[j];
                }
            }
        })
        // 楼盘单价
        let priceUint = getEleWithDefault(function () {
            return li.getElementsByClassName("unitPrice")[0].textContent;
        }, "");
        let priceTotal = getEleWithDefault(function () {
            return li.getElementsByClassName("totalPrice")[0].textContent;
        }, "");
        let house = {
            detailUrl: detailUrl,
            name: title,
            imgUrl: imgUrl,
            location: location,
            area: houseArea,
            priceUint: priceUint.replace(/\s+/g, ""),
            priceTotal: priceTotal.replace(/\s+/g, ""),
            type: houseType,
            year: houseYear,
            level: houseLevel,
            direction: houseDirection
        };
        console.log(house.detailUrl, house.name, house.location, house.imgUrl, house.area, house.priceUint, house.priceTotal, house.type, house.direction);
        houses.push(house);
    }
    return {"houses": houses, "total": resultCount};
}

function parseDjlXinFang(dom) {
    let resultCount = getEleWithDefault(function () {
        return Number(dom.getElementsByClassName("resultit")[0].getElementsByTagName("i")[0].textContent)
    }, 0)
    let lis = getEleWithDefault(function () {
        return dom.getElementsByClassName("xflists")[0].getElementsByTagName("li");
    }, undefined);
    let houses = [];
    if (lis === undefined || resultCount === 0) {
        return houses;
    }
    for (let i = 0; i < lis.length; i++) {
        let li = lis[i];
        let imgA = getEleWithDefault(function () {
            return li.getElementsByClassName("playA")[0];
        }, undefined);
        // 详情地址
        let detailUrl = getEleWithDefault(function () {
            return imgA.attributes["href"].nodeValue;
        }, "");
        // 楼盘名称
        let title = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["alt"].nodeValue;
        }, "");
        // 图片地址
        let imgUrl = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["src"].nodeValue;
        }, "");
        let priceFirst = getEleWithDefault(function () {
            return li.getElementsByClassName("fymax")[0].getElementsByTagName("strong")[0].textContent;
        }, "");
        let priceSecond = getEleWithDefault(function () {
            return li.getElementsByClassName("fymax")[0].getElementsByTagName("p")[0].textContent;
        }, "");
        // 楼盘地址
        let location = ""
        // 楼盘户型 房屋面积
        let houseArea = "";
        let houseType = "";
        getEleWithDefault(function () {
            let ps = li.getElementsByTagName("p");
            for (let j = 0; j < ps.length; j++) {
                let p = ps[j];
                if (p.textContent.includes("m²")) {
                    houseArea = p.textContent
                } else if (p.getElementsByTagName("a").length >= 1) {
                    location = p.getElementsByTagName("a")[0].textContent;
                }
            }
        }, "");
        let house = {
            detailUrl: detailUrl,
            name: title,
            imgUrl: imgUrl,
            location: location,
            area: houseArea,
            priceFirst: priceFirst,
            priceSecond: priceSecond,
            type: houseType
        };
        console.log(house.detailUrl, house.name, house.location, house.imgUrl, house.area, house.priceFirst, house.priceSecond, house.type);
        houses.push(house);
    }
    return {"houses": houses, "total": resultCount};
}

function parseDjlErShou(dom) {
    let resultCount = getEleWithDefault(function () {
        return Number(dom.getElementsByClassName("fymitit")[0].getElementsByTagName("strong")[0].textContent)
    }, 0)
    let lis = getEleWithDefault(function () {
        return dom.getElementsByClassName("fylist")[0].getElementsByTagName("li");
    }, undefined);
    let houses = [];
    if (lis === undefined || resultCount === 0) {
        return houses;
    }
    for (let i = 0; i < lis.length; i++) {
        let li = lis[i];
        let imgA = getEleWithDefault(function () {
            return li.getElementsByClassName("fyimg")[0].getElementsByTagName("a")[0];
        }, undefined);
        // 详情地址
        let detailUrl = getEleWithDefault(function () {
            return imgA.attributes["href"].nodeValue;
        }, "");
        // 楼盘名称
        let title = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["alt"].nodeValue;
        }, "");
        // 图片地址
        let imgUrl = getEleWithDefault(function () {
            return imgA.getElementsByTagName("img")[0].attributes["src"].nodeValue;
        }, "");
        let location = "";
        let houseLevel = "";
        let houseYear = "";
        let houseArea = "";
        let houseDirection = "";
        let houseType = "";
        getEleWithDefault(function () {
            let info = li.getElementsByClassName("fyfn")[0].textContent;
            let info_list = info.replace(/\s+/g, "").split("|");
            location = info_list[0];
            for (let j = 1; j < info_list.length; j++) {
                if (info_list[j] === "东" || info_list[j] === "西" || info_list[j] === "南" || info_list[j] === "北" || info_list[j] === "东北" || info_list[j] === "东南" || info_list[j] === "西南" || info_list[j] === "西北") {
                    houseDirection = info_list[j];
                } else if (info_list[j].includes("年建")) {
                    houseYear = info_list[j];
                } else if (info_list[j].includes("m²")) {
                    houseArea = info_list[j];
                } else if (info_list[j].includes("室") || info_list[j].includes("厅") || info_list[j].includes("卫")) {
                    houseType = info_list[j];
                }
            }
            let info_list2 = li.getElementsByClassName("fydz")[0].textContent.replace(/\s+/g, "").split("|");
            for (let j = 0; j < info_list2.length; j++) {
                if (info_list[j].includes("楼层")) {
                    houseLevel = info_list[j];
                } else if (info_list[j].includes("年建")) {
                    houseYear = info_list[j];
                }
            }
        })
        // 楼盘单价
        let priceUint = getEleWithDefault(function () {
            return li.getElementsByClassName("fymax")[0].getElementsByTagName("p")[0].textContent;
        }, "");
        let priceTotal = getEleWithDefault(function () {
            return li.getElementsByClassName("fymax")[0].getElementsByTagName("strong")[0].textContent;
        }, "");
        let house = {
            detailUrl: detailUrl,
            name: title,
            imgUrl: imgUrl,
            location: location,
            area: houseArea,
            priceUint: priceUint.replace(/\s+/g, ""),
            priceTotal: priceTotal.replace(/\s+/g, ""),
            type: houseType,
            year: houseYear,
            level: houseLevel,
            direction: houseDirection
        };
        console.log(house.detailUrl, house.name, house.location, house.imgUrl, house.area, house.priceUint, house.priceTotal, house.type, house.direction);
        houses.push(house);
    }
    return {"houses": houses, "total": resultCount};
}