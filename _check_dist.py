import os

dist = r'd:\workspace\workspace_2\wanxiangdakao\万象大考v3\dakao\dist\build\mp-weixin'

# Check app.json
app_json = os.path.join(dist, 'app.json')
print('=== app.json ===')
with open(app_json, 'r', encoding='utf-8') as f:
    content = f.read()
print(content[:2000])

print('\n=== All WXML/WXSS/JS files ===')
for root, dirs, files in os.walk(dist):
    for f in sorted(files):
        if f.endswith(('.wxml','.wxss','.js','.json')):
            full = os.path.join(root, f)
            rel = full.replace(dist, 'dist').replace('\\', '/')
            print(f'  {rel}')
