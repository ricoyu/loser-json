classpath根目录下放jackson.properties, 支持的配置项如下

1. jackson.propertes.ignore_case=false

   JSON转POJO时, 是否支持属性名大小写不敏感匹配, 默认false

2. jackson.fail.on.unknown.properties=false

   JSON转POJO时, 某属性JSON中有, POJO中没有时, 是否报错, 默认不报错