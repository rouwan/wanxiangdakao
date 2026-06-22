# -*- coding: utf-8 -*-
"""出题脚本 - 直接 INSERT 数据库，不走后台"""
import subprocess, sys, os

MYSQL = r"D:\work_software\mysql5.7.44\mysql-5.7.44-winx64\bin\mysql.exe"

def run(sql, db="wxdkgamedo"):
    r = subprocess.run(
        [MYSQL, '--default-character-set=utf8mb4', '-u', 'root', db, '-e', sql],
        capture_output=True)
    out = r.stdout.decode('utf-8', errors='replace')
    err = r.stderr.decode('utf-8', errors='replace')
    if r.returncode != 0 and err:
        print("[SQL ERROR]", err[:300])
    return out.strip(), r.returncode

def esc(s):
    return s.replace("\\", "\\\\").replace("'", "\\'")

# ============== 在这填题 ==============
Q = {
    "prompt":       "中国最大的淡水湖是哪个?",   # 题干
    "options": [
        "鄱阳湖",   # option 1
        "洞庭湖",   # option 2
        "太湖",     # option 3
        "青海湖",   # option 4
    ],
    "answer_index": 1,        # 正确答案是第几个 (1/2/3/4)
    "typeId":       11,        # 分类 (1文学 10历史 11地理 12数学 13物理 14化学 15生物 ...)
    "explanation":  "鄱阳湖位于江西省北部，中国第一大淡水湖。青海湖面积更大但是咸水湖。",
    "author":       "AI出题",
}

# ============== 执行（不用改） ==============
answer_text = Q["options"][Q["answer_index"] - 1]

# sortId
out, _ = run("SELECT IFNULL(MAX(sortId),0)+1 AS ns FROM wxdk_subject")
next_sort = int(out.split('\n')[1])

# INSERT subject
sql = ("INSERT INTO wxdk_subject "
       "(promptWord, answer, description, optionSortId, typeId, `type`, sortId, status, modeType, createUserName, createTime, modiftTime) "
       "VALUES ('{0}', '{1}', '{2}', {3}, {4}, 1, {5}, 1, 3, '{6}', NOW(), NOW())").format(
    esc(Q["prompt"]), esc(answer_text), esc(Q["explanation"]),
    Q["answer_index"], Q["typeId"], next_sort, esc(Q["author"]))
out, rc = run(sql)
if rc != 0:
    sys.exit(1)

# Get new id
out, _ = run("SELECT MAX(id) FROM wxdk_subject")
new_id = int(out.split('\n')[1])

print("")
print("Question created: id={}".format(new_id))
print("  Prompt: {}".format(Q["prompt"]))
print("  Answer: {} (option #{})".format(answer_text, Q["answer_index"]))

# INSERT options
for i, opt in enumerate(Q["options"], 1):
    sql = ("INSERT INTO wxdk_subjectoption "
           "(subjectId, `option`, sortId, createTime, modifyTime) "
           "VALUES ({}, '{}', {}, NOW(), NOW())").format(new_id, esc(opt), i)
    run(sql)
    tag = "==>" if i == Q["answer_index"] else "   "
    print("  {} {}. {}".format(tag, i, opt))

print("")
print("Verify: SELECT * FROM wxdk_subject WHERE id={}".format(new_id))
print("Options: SELECT * FROM wxdk_subjectoption WHERE subjectId={}".format(new_id))
