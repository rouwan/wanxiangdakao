import re

fp = r'd:\workspace\workspace_2\wanxiangdakao\v3\前台前端\dist\pages\preGamePage\index\js\main.js'
with open(fp, 'r', encoding='utf-8', errors='replace') as f:
    c = f.read()

# Fix: add guard before giftRankList.reverse()
old = r'_this9.giftRankList = JSON.parse(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(_this9.info)).userParagraphList.reverse();'
new = r'if(_this9.info&&_this9.info.userParagraphList){_this9.giftRankList = JSON.parse(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(_this9.info)).userParagraphList.reverse();'
if old in c:
    c = c.replace(old, new)
    with open(fp, 'w', encoding='utf-8', errors='replace') as f:
        f.write(c)
    print('Patched: giftRankList.reverse() guarded')
else:
    print('Pattern not found - checking variants...')
    # Try partial match
    idx = c.find('.giftRankList = JSON.parse')
    if idx > 0:
        print(f'Found at {idx}: ...{c[idx-50:idx+80]}...')
    else:
        print('No giftRankList found at all')
