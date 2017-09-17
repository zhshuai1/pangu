#http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/
#https://baike.baidu.com/item/%E5%8F%B0%E6%BE%8E%E9%87%91%E9%A9%AC/8957368

counter = 10000
pcds = []
id_obj_map = {}

def mask value, msk
    puts "invalid input: " + value + msk if value.size != 6 or msk.size !=6
    result = ""
    for i in 0...6
        result << (msk[i] == "0" ? "0" : value[i])
    end
    result
end

def is_province? value
    (mask value, "001111") == "000000"
end

def is_city? value
    (mask value, "000011") == "000000"
end

def parent value
    return "0" if value == "000000"
    return "000000" if is_province? value
    return (mask value, "110000") if is_city? value
    (mask value, "111100")
end

File.open("pcd","r") do |file|
    while line = file.gets
        pcd = {}
        lc = line.split
        (puts line + " is an invalid line"; next) if not lc or lc.size < 2
        
        code = lc[0]
        prefix = (is_province? code) ? "Province" : (is_city? code) ? "City" : "District"
        prefix = "Country" if code == "000000"
        prefix = "CN:" + prefix
 
        pcd["code"] = code
        pcd["comment"] = prefix + ":" + code
        pcd["desc"] = lc[1]
        pcd["id"] = counter += 1
        pcd["parent"] = id_obj_map[parent code]
        pcds << pcd
 
        id_obj_map[code] = pcd["id"]
        (id_obj_map[code] = -1 ; pcd["parent"] = pcd["id"]) if lc[1].include? "直辖县级"
        
        
        if pcd["parent"] == -1
            pcd_clone = pcd.clone
            pcd["code"] = code + "X"
            pcd["comment"] = prefix + ":" + code + "X"
            pcd_clone["id"] = counter += 1
            pcd_clone["parent"] = pcd["id"]
            pcd["parent"] = id_obj_map[parent (parent code)]
        
            pcds << pcd_clone
        end
    end
end

File.open("pcd_records","w") { |file| 
    pcds.each do |p|
        (puts p; p["parent"] = 0) until p["parent"]
        file.printf("%s\t%d\t%d\t%s\n",p["comment"],p["id"],p["parent"],p["desc"])
    end
}

