counter = 6000
colleges = []
id_obj_map = {}

File.open("city-state-mapping","r") do |file|
    while line = file.gets
        fields = line.split
        name = fields[1]
        parent = fields[3]
        location = {}
        location["id"] = counter += 1
        location["desc"] = name
        location["parent"] = id_obj_map[parent] || 0
        location["comment"] = "College:"
        id_obj_map[name] = location["id"]
        colleges << location
    end
end
puts colleges
File.open("colleges","r") do |file|
    while line = file.gets
        college = {}
        lc = line.split
        if not lc or lc.size < 6
            puts "!!!!!!!!!!!!!!!!!!!!!!!!!!!"
            puts line
            next
        end
        name = lc[1].gsub(/"/,"")
        code = lc[2]
        city = lc[4].gsub(/"/,"")
        college["code"] = code
        college["comment"] = "College:" + code
        college["desc"] = name
        college["id"] = counter += 1
        college["parent"] = id_obj_map[city] || 0
        colleges << college
    end
end

File.open("college_records","w") { |file| 
    colleges.each do |c|
        file.printf("%s\t%d\t%d\t%s\n",c["comment"],c["id"],c["parent"],c["desc"])
    end
}

