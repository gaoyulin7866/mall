# 版本
```
 - java-1.8
 - mysql-8.0.26
 - zookeeper-3.6.3
 - redis-7.0.0
```

# 文件目录

```
- mall
 - shopping-api
   - pom.xml
   - com.gyl.shopping
     - api
       - CartDubboService // 给mall-order提供dubbo服务调用
       - CartService
       - CategoryService
       - OrderService
       - ProductService
       - UserService
     - dto
       - Cart
       - Category
       - Order
       - OrderItem
       - Product
       - User
     - vo
       - CartVo
       - CategoryVo
       - CategoryTileVo
       - OrderVo
       - OrderItemVo
       - ProductVo

 - shopping-common
   - pom.xml
   - com.gyl.shopping
     - common
       - Constants
       - EmailUtils      // 邮箱校验以及随机生成四位验证码
       - ExceptionEnum
       - MallException
       - Md5Utils        // md5密码加密
       - OrderNoUtil     // 生成订单号码
       - OrderStatus
       - QrcodeUtil
       - ResultCode
       - ResultResponse
       - RSAUtil         // RSA publicKey privateKey
       - TokenUtil       // RSA256加密userid生成token 前端放header中
       - UserRule
 - shopping-server
   - pom.xml
   - resources
     - config
       - local.properties // 本地启动服务配置 暂未上传
       - online.properties// 阿里云启动服务配置 暂未上传
     - mappers
       - ...
     - application.properties // 暂未上传
     - generatorConfig.xml // 生成mappers等配置 暂未上传
     - shopping.sql        // 初始化sql表
   - com.gyl.shopping
     - config
       - JedisConfig
       - UserFilterConfig // 配置userFilter
       - ThreadPoolConfig // 初始化线程池
     - controller
       - OrderController  // 调用mall-order的dubbo服务
       - ...
     - filter
       - UserFilter       // 实现userFilter 校验并在当前线程存储user信息
     - ShoppingApplication
 - shopping-service
   - pom.xml
   - com.gyl.shopping
     - dao
       - ...
     - service
       - impl
         - CartDubboServiceImpl // dubbo服务实现
         - CartServiceImpl
         - CategoryServiceImpl
         - ProductServiceImpl
         - UserServiceImpl
 - pom.xml
```
