#!/bin/bash
cd "${0%/*}"

name=$1
upperName="$(echo "${name:0:1}" | tr a-z A-Z)${name:1}"

sed -i '' "s#/\\*module\\*/#, '$name'&#" settings.gradle
cp -r "modules/_template" "modules/$name"
mv "modules/$name/src/main/java/com/teamwizardry/librarianlib/template/" "modules/$name/src/main/java/com/teamwizardry/librarianlib/$name/"
mv "modules/$name/src/main/kotlin/com/teamwizardry/librarianlib/template/LibLibTemplate.kt" "modules/$name/src/main/kotlin/com/teamwizardry/librarianlib/template/LibLib$upperName.kt"
mv "modules/$name/src/main/kotlin/com/teamwizardry/librarianlib/template/" "modules/$name/src/main/kotlin/com/teamwizardry/librarianlib/$name/"
mv "modules/$name/src/main/resources/librarianlib-template.client.json" "modules/$name/src/main/resources/librarianlib-$name.client.json"
mv "modules/$name/src/main/resources/librarianlib-template.common.json" "modules/$name/src/main/resources/librarianlib-$name.common.json"

deps=$(for dep in "${@:2}" 
do
    echo "    compile project(':$dep')\\"
done; echo -n "}")

grep -r --fixed-strings "template" -l --null "modules/$name" | xargs -0 sed -i '' "s#template#$name#g"
grep -r --fixed-strings "Template" -l --null "modules/$name" | xargs -0 sed -i '' "s#Template#$upperName#g"
sed -i '' "s#/\\*deps\\*/}#$deps#g" "modules/$name/build.gradle" 
