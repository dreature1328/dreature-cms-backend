# 基于 Spring Cloud 的微服务博客系统

本项目是一个采用微服务架构的博客系统，基于 Spring Cloud，提供用户支持、内容浏览、社区互动、后台管理等功能。

## 核心架构与技术栈

> 后端基于 Spring Boot 开发，利用 Spring Cloud 技术栈实现服务治理与分布式能力。系统功能通过多个独立微服务实现，使用 Nginx + Spring Cloud Gateway 进行反向代理与网关路由。数据存储结合 MySQL 持久化、Redis 缓存热点数据，ORM 框架采用 MyBatis。

**开发与数据处理**

- **基础框架：** Spring Boot
- **数据访问：** MyBatis

**微服务框架**

- **分布式核心构建**：Spring Cloud
- **服务注册与发现：** Eureka
- **API 网关：** Spring Cloud Gateway
- **客户端负载均衡：** Spring Cloud LoadBalancer
- **集中配置管理：** Spring Cloud Config

**基础设施/中间件**

- **反向代理与入口层：** Nginx
- **网关层：** Spring Cloud Gateway
- **数据持久化：** MySQL
- **缓存：** Redis（热点数据缓存）

## 微服务划分

核心业务服务

- 用户服务（User Service）：登录注册等
- 内容服务（Content Service）：文章呈现、分类筛选、热度排行等
- 交互服务（Interaction Service）：点赞收藏等
- 评论服务（Comment Service）：评论等
- 媒体服务（Media Service）：图片上传等

基础设施服务

- 网关服务（Gateway Service）：路由转发、过滤等
- 注册服务（Registry Service）：服务注册等
- 配置服务（Config Service）：统一配置等

## 启动流程

在 `dreature-cms-config` 中完成配置后，将配置文件提交至远程仓库。其后在配置服务 （`dreature-cms-infra-config`）的 `application.properties` 中应用上述配置。

按以下顺序运行各微服务的启动类 `Application.java`：注册服务 -> 配置服务 -> 网关服务 -> 业务服务。



