package com.finance.trade_learn.data

class Cache_Data() {

    fun coinsName():ArrayList<String>{
        val coinsNamePush=ArrayList<String>()
        coinsNamePush.addAll(arrayListOf("BTC","ETH","HEX","BNB","ADA","USDT","XRP","SOL","DOT","USDC","DOGE","UNI","LUNA","SHIB",
            "BUSD","WBTC","LTC","AVAX","LINK","ALGO","BCH","FTT","MATIC","ATOM","XLM","AXS","VET","FIL","ICP","ETC","TRX","DAI",
            "THETA","CETH","XTZ","HBAR","STETH","FTM","XMR","EGLD","CRO","CAKE","EOS","OKB","KLAY","CDAI","NEAR","AAVE","BCHA",
            "BSV","NEO","KSM","WAVES","UST","AR","STX","LEO","SUSHI","BTT","ONE","CEL","MKR","AMP","HNT","1INCH","RUNE","OMG",
            "DCR","HOT","XEM","TFUEL","OMI","ENJ","XDC","ZEC","ICX","QTUM","TTT","DYDX","YFI","TUSD","ECOIN","TEL","BTG","HT",
            "CUSDC","QNT","IOT","GRT", "DASH","COMP","CELO","SNX","CHZ","NEXO","KCS","BNT","SC","ZEN","MDX","XWC","ZRX",
            "ZIL","CRV","IOST","FLOW","MINA","KOKO","RVN","REN","SAFEMOON","BAT","MFG","SRM","PERP","MANA","USDP",
            "AUDIO","ONT","OGN","CELR","CUSDT","RAY","NANO","DGB","VGX","SKL","ANKR","GT","SAND","LN","DODO","IOTX","GALA",
            "UMA","POLY","ETHM","NU","CHSB","LPT","WRX","USD-N","DENT","GNT","KAVA","RPL","WOO","FET","DAG","FX","GNO","LRC",
            "LSK","ALPHA","TRIBE","EWT","BAL","TITAN","COTI","XDB","SXP","RSR","RGT","ERG","WIN","WAXP","BCD","CKB","ARRR",
            "FRAX","PLEX","BAKE","XVG","XCP","TWT","VTHO","LYXE","INJ","SETH","NXM","MED","ETN","RAD","VXV","XGOLD","OCEAN",
            "XYO","SNT","BTCST","UNIC","ORBS","KDA","RLC","TON","WAX","ARDR","KEEP","VLX","BAND","CVC","FIDA","USDN","CFX",
            "REEF","PAXG","BADGER","XVS","STMX","MASK","ASD","SAPP","UBT","PROM","ROSE","XCH","STRAX","HIVE","ELF","SBTC",
            "STORM","CTSI","ALCX","CSPR","ARK","TLNT","DERO","MAID","NMR","SUPER","DAWN","ORN","USDP","NKN","STEEM","XPR",
            "TON","FXS","TRU","KLV","LDO","FUN","TLOS","FLEX","ALBT","TRAC","MTL","ACH","ELG","ATLAS","QKC","OXT","BDX","IQ",
            "DPI","CP","RIF","PLA","API3","TOMO","LEASH","VRA","MLN","ANY","STPT","AETH","POLS","MNC","STORJ","CHR","MATH","META",
            "IDEX","WAN","POLIS","NDAU","UTK","SYS","ANT","ERN","LEND","REPV2","NOIA","RLY","YFII","POWR","AVA","C20","REQ",
            "HYDRA","HTR","KNCL","UQC","LINA","PBTC","XAUT","PHA","MRPH","SNOW","PRE","UOS","HXRO","ALPACA","MIST","SUN",
            "DDX","KRN","GUSD","RNDR","KIN","EXRD","KMD","MCB","SUSD","NWC","ARPA","BEL","BTS","FORTH","GAS","RARI","MFT",
            "WISE","SBIT","DVI","AMPL","CSC","IRIS","XOR","MX","WHALE","AFN","BTCV","WNXM","TPT","HOLY","LOC","BOND","KOIN",
            "AKT","STRONG","DNT","SLX","JST","ORCA","ZKS","CREAM","FOX","VAL","CORE","MXC","FARM","TT","EURS","RAMP","ZNN",
            "SWAP","CRE","DATA","XSGD","ROOK","XHV","PAC","BZRX","SEUR","HEGIC","TRB","CUSD","DG","TOMOE","HNS","CDT","LOOM",
            "AQT","ADK","VID","GHST","BIFI","NRG","OM","XZC","BTM","BEPRO","KP3R","DIVI","AERGO","TVK","DIA","AUTO","ELA",
            "RFR","AION","RDD","KAI","VELO","AKRO","GIO","HUNT","XCM","EURT","BTSE","LTO","BEAM","TORN","GRS","FWB","FLUX",
            "INDEX","VRSC","HARD","BLZ","COS","NFTX","PSG","LCX","POND","CRETH2","ADX","CBK","ETHBULL","FLM","QC","DAD",
            "STAKE","FRONT","WOZX","CTK","PRQ","SOUL","RAI","GZIL","NSBT","CRU","PNK","MONSTA","FIO","HVE2","SBD","UPP",
            "PRO","VITE","GMAT","DUSK","UFT","LEOS","HEZ","GMT","SENT","KOBE","CHAIN","CUT","EDG","DF","DGD","YLD","MET",
            "ALEPH","SUKU","SKEY","TRYB","MTV","ERSDL","BNS","LON","GET","NIM","ATRI","URUS","SLT","PIVX","GAT","SERO","BAR",
            "ARV","NULS","CVP","ONX","FSN","QASH","UNFI","DHT","BTU","DRGN","VERI","SNTR","CJX","WING","SNL","VIDT","BMX","MBX",
            "GNY","FIS","WANATHA","FOR","MOC","APL","DEGO","DEXE","WOM","APY","RBC","DEXT","HOO","MWC","SPARTA","QSP","GXC",
            "SFI","USDK","AST","CXO","AE","NPXS","MUSD","LTX","VSYS","CTXC","OXEN","PLTC","ASK","EMAX","JUV","TROY","KRT",
            "MBL","DAPS","STRAT","ZANO","MTRG","NAV","HAI","COCOS","SAN","PNT","ZB","PULSEDOGE","HIBS","GRIN","VEE","ULT",
            "DSLA","SOLVE","RDN","DEP","HTB","MASS","GO","TIME","SAITO","MPH","PHX","CND","MITH","VTC","SRK","REVV","PALG",
            "FRM","XED","SLP","BOA","GAL","CUBE","MIX","WICC","PART","ELX","ZEE","XCUR","YCC","NIF","MHC","DUCK","CARD",
            "DXD","ETZ","WTC","PPT","VAL","COVAL","ARN","YOP","OUSD","PRV","GTO","WPP","PEPECASH","MAP","HYN","HYVE","KEY",
            "NGC","SALT","XEP","FXF","SKY","FOAM","SIGNA","MDT","ADS","PPC","NEBL","JUP","RING","BUY","SOC","NEST","SWTH",
            "EMB","LBC","BRTK","CPAY","DASHD","DEC","MITX","WOW","SHA","INSTAR","BAX","DOUGH","BOT","BAO","LAMB","AMB","BTC2",
            "BZ","CLU","ZCN","MTA","SNTVT","AXEL","PIB","BDT","NXT","LAYER","GBYTE","FWT","DCN","TSD","AFIN","NCT","GRO","BRD",
            "SUTER","NEX","TCT","EL","DTX","IGNIS","POP","NAS","XSN","LCC","DBC","FREE","VETH","KAN","KEX","MYST","RFUEL",
            "AXN","PPAY","SIX","DDIM","XRT","DEC","ORAI","SMART","CHI","FCT","SYLO","ETP","BSCX","PICKLE","QRL","GVT","YFL",
            "CRPT","TXL","SWINGBY","EUNO","ICHI","MINDS","UNCX","ABT","ENQ","MDA","ZT","DIGG","MTH","ETL","HDP.Ф","WABI","NEC",
            "DUCK","BAN","GRID","PNY","EVX","CFI","UBQ","KOIN","RCN","OAX","SHROOM","MM","JRT","SPANK","USELESS","UBXT","INT",
            "WXT","ROBOT","CAS","EXNT","FSCP","BOND","PROB","BAAS","EPIC","NANJ","OVR","RAE","XPAT","BONDLY","OAP","BABYCAKE",
            "MUSE","CBC","EMC","SFIL","KRL","APM","BOX","TRTL","POA","SCC","MWAT","GTH","COV","DMD","THT","ZAP","HVN","BANK",
            "ELONONE","ACS","VALOR","UWL","BIS","VIB","SUPER","FEED","SPC","FUSE","SWFTC","YFIII","AMLT","PUT","CNFI","AWX","OLT",
            "LNC","QRK","BLOCK","SCP","SWRV","RSV","XFT","VITAE","REV","ONES","APPC","TIPS","NLG","OWC","IQN","UIP","CS",
            "MOBI","XDN","ARCH","TNB","PLU","VIDYA","SATT","REAP","1UP","BLT","DAC","NVT","CWBTC","TOP","UDOO","LUA","CHP",
            "DLT","MAN","AVT","QLC","GEEQ","CNS","DOV","PLR","MTLX","EASY","ENG","TEN","LYM","EGT","GSWAP","IDH","PI","CV",
            "WGR","MAHA","VNLA","UNN","OCE","CPC","VEX","UFO","LA","XMX","GHOST","MELLO","DEFIDO","IDRT","NCASH","PAY","MEONG",
            "1337","OCN","IDLE","CMT","TRA","NYZO","SNM","FTC","SPC","MTC","XMY","AMP","GRG","ROOBEE","XPX","SPH","LND","REDPANDA",
            "STQ","AOA","ACT","AVINOC","BITCNY","MIDAS","BIRD","BLK","UMX","YAM","HIT","XCASH","ABYSS","BIX","FIN","PRCY","PND",
            "ONION","OIN","DAGT","KAT","DYN","ZOO","MTGY","FNT","EMT","SFD","HAKKA","ABL","ANRX","VIA","UNIDX","TUSC","BCDT","IMT",
            "EFL","SHIBX","BUIDL","TLM","BCN"))
        return coinsNamePush
    }
}


