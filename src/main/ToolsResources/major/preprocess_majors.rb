#data source: 普通高等学校本科专业目录
counter = 4000
majors = []
id_obj_map = {}

File.open("majors","r") do |file|
    while line = file.gets
        major = {}
        lc = line.split
        next if not lc or lc.size < 2

        code = lc[0]
        major["code"] = code
        major["comment"] = "Major:" + code
        major["desc"] = (lc[1].match(/[\u4e00-\u9fa5]+/))[0]
        major["id"] = counter += 1
        majors << major
        id_obj_map[code] = major["id"]
    end
    majors.each do |m|
        num = (m["code"].match(/\d+/))[0]
        parent = num[0,num.size-2]
        m["parent"] = id_obj_map[parent]
    end
end

File.open("major_records","w") { |file|
    majors.each do |m|
        m["parent"] = "0" if not m["parent"]
        file.printf("%s\t%d\t%d\t%s\n",m["code"],m["id"],m["parent"],m["desc"])
    end
}
