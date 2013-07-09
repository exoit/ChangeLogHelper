ChangeLogHelper
===============
This library makes it easy to show your app's changelog in an activity.

Example
------
![Changelog Example Screenshot][1]


Usage
-----
Create your changelog xml file using the format shown below and add it to your **res/xml** directory.

```xml
<?xml version="1.0" encoding="utf-8"?>
<changelog>
    <release
        versionName="1.0.1"
        versionCode="2">
        <change>Added Awesome Stuff.</change>
        <change>Added More Awesome Stuff.</change>
    </release>
    <release
        versionName="1.0.0"
        versionCode="1">
        <change>Initial Release.</change>
    </release>
</changelog>
```

### Use the activity provided in the library

Add the ChangeLogActivity to your app's AndroidManifest.

```xml
<activity
    android:name="se.puzzlingbytes.changeloghelper.ChangeLogActivity"
    android:label="@string/changelog_title" >
</activity>
```
Start the activity either by generating an intent with **getChangeLogIntent(Context, ChangeLogXmlResID)** or by manually creating the intent with the changelog xml resource id as extra with the constant **EXTRA_CHANGELOG_XML_RESID** found in ChangeLogActivity.

### Manually handle the data

Example below shows how to parse and generate the HTML page.

```java
ChangeLogParser changeLogParser = new ChangeLogParser(context);
ChangeLog changeLog = changeLogParser.parse(changeLogXMLResID);
changeLog.generateChangeLogHTML()
```

License
-------
<pre><code>
Copyright 2013 Mikael Olsson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</code></pre>

[1]: https://raw.github.com/exoit/ChangeLogHelper/master/sample/changelog.png
