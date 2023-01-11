### 避免重绘与重排
为避免网页产生重绘和重排而大大影响网页性能，应遵守以下约定：

- 不在 HTML 标签上使用 `style` 属性，采用布局和样式分离的方式

- 不手动添加或者删除可见的 DOM 元素，尽量采用显示/隐藏的方式控制

- 不要使用 table 布局，很小的一个改动可能会造成整个 table 的重新布局

### 样式格式
样式统一采用展开格式，而非紧凑格式。

```css
/* bad */
.box{display: block;width: 50px;}

/* good */
.box {
  display: block;
  width: 50px;
}
```

### 选择器
应尽量少用通用选择器 `*`，不使用 ID 选择器，不使用无具体语义的标签选择器。

```css
/* bad */
* {}
#box {}
.box div {}
```

### 缩进
统一使用`两个空格`进行代码缩进。

```css
/* good */
.box {
  width: 100%;
  height: 100%;
}
```

### 分号
每个属性末尾都需要加分号。

```css
/* bad */
.box {
  width: 100%
  height: 100%
}

/* good */
.box {
  width: 100%;
  height: 100%;
}
```

### 属性值
**1. 十六进制色值能简写则简**

```css
/* bad */
.box {
  color: #ffffff;
}
/* good */
.box {
  color: #fff;
}
```

**2. 不为 0 指定单位**

```css
/* bad */
.box {
  margin: 0px 10px;
}

/* good */
.box {
  margin: 0 10px;
}
```

### 属性书写顺序
属性的顺序遵循一定的规则有利于代码可读性，可降低样式覆盖的误操作率，建议遵循以下书写顺序：

1. 布局定位属性：display / position / float / clear / visibility / overflow
2. 自身属性：width / height / margin / padding / border / background
3. 文本属性：color / font / text-decoration / text-align / vertical-align / white- space / break-word
4. 其他属性（CSS3）：content / cursor / border-radius / box-shadow / text-shadow / background:linear-gradient …

```css
.sun {
  display: block;
  position: relative;
  float: left;
  width: 100px;
  height: 100px;
  margin: 0 10px;
  padding: 20px 0;
  font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;
  color: #333;
  background: rgba(0,0,0,.5);
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  -o-border-radius: 10px;
  -ms-border-radius: 10px;
  border-radius: 10px;
}
```

### 媒体查询位置
将媒体查询的样式放置在与其相关的样式附近，而不是单独放在一个样式文件或无规则地放置在样式的末尾。

```css
/* good */
.element { ... }
.element-avatar { ... }
.element-selected { ... }

@media (min-width: 480px) {
  .element { ...}
  .element-avatar { ... }
  .element-selected { ... }
}
```