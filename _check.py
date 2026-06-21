import os

fp = r'd:\workspace\workspace_2\wanxiangdakao\v3\前台前端\dist\pages\preGamePage\index\js\main.js'
if not os.path.exists(fp):
    print('File not found:', fp)
    # Search
    for root, dirs, files in os.walk(r'd:\workspace\workspace_2\wanxiangdakao\v3\前台前端\dist\pages\preGamePage'):
        for f in files:
            print(' ', os.path.join(root, f))
    exit()

with open(fp, 'r', encoding='utf-8', errors='replace') as f:
    c = f.read()

# Find all .reverse() calls and show context
import re
for m in re.finditer(r'\.reverse\(\)', c):
    start = max(0, m.start() - 150)
    end = min(len(c), m.end() + 20)
    ctx = c[start:end]
    print(f'--- .reverse() at offset {m.start()} ---')
    print(ctx)
    print()
