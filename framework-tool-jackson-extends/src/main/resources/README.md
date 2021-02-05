## 说明

扩展内容<br/>
1.扩展自定义枚举转换的方式<br/>


#### 1.扩展JackSon的枚举转换工作

#### 使用方式

```java

ObjectMapper objectMapper=new ObjectMapper();
SimpleModule simpleModule=new SimpleModule();
simpleModule.addSerializer(IEnum.class, new NumEnumSerializer());
simpleModule.addDeserializer(Enum.class,new NumEnumDeserializer(Enum.class));
objectMapper.registerModule(simpleModule);

```

## 自定义枚举转换解析