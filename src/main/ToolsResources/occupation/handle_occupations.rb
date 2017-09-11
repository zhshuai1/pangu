counter = 5000
occs = []
id_obj_map = {}

def compose_key arr
    s_arr = arr.map do |n|
        "%02d"%n
    end
    s_arr.join("-")
end

def recompose_key str
    arr = str.split("-").map do |s|
        s.to_i
    end
    compose_key arr
end

File.open("occ_final","r") do |file|
    while line = file.gets
        occ = {}
        m = line.split
        m.unshift "padding"
        if not m[1]
            puts "!!!!!!!! id is empty !!!!!!!!!"
            puts line
            next
        end
        code = recompose_key m[1]
        if not id_obj_map[code] and (not m[2] or m[2].empty?)
            puts "............................."
            puts line
            next
        end
        next if id_obj_map[code]
        occ["code"] = code
        occ["comment"] = "Job:" + code
        occ["desc"] = m[2]
        occ["id"] = counter += 1
        occs << occ
        id_obj_map[code] = occ["id"]
    end
    occs.each do |o|
        num = o["code"]
        parent = num[0,num.size-3]
        o["parent"] = id_obj_map[parent]
    end
end

File.open("occ_records","w") { |file| 
    occs.each do |m|
        m["parent"] = "0" if not m["parent"]
        file.printf("%s\t%d\t%d\t%s\n",m["code"],m["id"],m["parent"],m["desc"])
    end
}

