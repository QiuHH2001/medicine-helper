## 目录
- [语法](#语法)
    - [DOCTYPE 声明](#DOCTYPE声明)
    - [页面语言](#页面语言)
    - [字符编码](#字符编码)
    - [元素及标签闭合](#元素及标签闭合)
- [代码风格](#代码风格)
    - [大小写](#大小写)
    - [元素属性](#元素属性)
    - [元素及标签闭合](#元素及标签闭合)
- [文件模板](#文件模板)
    - [PC端模板](#PC端模板)
    - [移动端模板](#移动端模板)

## 语法
### DOCTYPE声明
HTML 文件必须加上 DOCTYPE 声明，并统一使用 HTML5 的文档声明：

```html
<!DOCTYPE html>
```

### 页面语言
页面根元素 `<html>` 上可使用 `lang` 属性指定页面语言，有助于语音合成工具、翻译工具确认规则等，统一使用**中文简体**：`zh-CN`。

```html
<html lang="zh-CN">
    <!-- ... -->
</html>
```

### 字符编码
显式声明字符编码能正确呈现页面内容，统一使用 UTF-8 编码。

```html
<meta charset="UTF-8">
```

> 注意 `UTF-8` 的标准写法，不可写成 `utf-8` 或 `UTF8`。

### 元素及标签闭合
HTML 规定，某些元素的开始和结束标签是可以省略的，而空元素标签如 `<br>`、`<img>` 等的结束符号 `/` 是可选的，都不影响浏览器的正常解析。为了代码可读性，团队约定：

- 具有开始和结束标签的元素都要写完善起止标签
- 空元素标签都不加 `/`

```html
<!-- bad -->
<div>
    <h1>我是h1标题</h1>
    <p>我是一段文字，我有始无终，浏览器亦能正确解析
</div>

<br/>

<!-- good -->
<div>
    <h1>我是h1标题</h1>
    <p>我是一段文字，我有始有终，浏览器能正确解析</p>
</div>
	
<br>
```

### 属性顺序
HTML 元素的属性顺序虽不影响页面效果，但当元素属性较多时不易查找阅读，应尽量按照以下特定顺序排列：

- `class`
- `id`，`name`
- `src`，`for`，`type`，`href`，`value`
- `title`，`alt`
- `role`

```html
<!-- bad -->
<img src="../static/header.jpg" title="header" id="myImg" alt="header img"  class="header-img">

<!-- good -->
<img class="header-img" id="myImg" src="../static/header.jpg" title="header" alt="header img">
```

## 代码风格
### 大小写
标签名、属性、类名一律采用小写。

```html
<!-- bad -->
<DIV CLASS="DEMO"></DIV>

<!-- good -->
<div class="demo"></div>
```

### 元素属性
标签的属性值始终使用双引号，禁止使用单引号。

```html
<!-- bad -->
<img src='images/logo.png'>

<!-- good -->
<img src="images/logo.png">
```

### 嵌套元素
嵌套的块级元素应缩进一次，内联元素可不缩进。

```html
<!-- bad -->
<div>
  <h1></h1><p></p>
</div>
<p>
  <span></span>
  <span></span>
</p>

<!-- good -->
<div>
  <h1></h1>
  <p></p>
</div>
<p>
  <span></span><span></span>
</p>
```

## 文件模板
### PC端模板
```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="UTF-8">
  <meta name="keywords" content="your keywords">
  <meta name="description" content="your description">
  <title>PC 端 HTML 模版</title>
</head>

<body>

</body>

</html>
```

### 移动端模板
```html
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, shrink-to-fit=no">
  <meta name="format-detection" content="telephone=no">
  <title>移动端 HTML 模版</title>
</head>

<body>

</body>

</html>
```