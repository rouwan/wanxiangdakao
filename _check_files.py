import os

d = 'd:/workspace/workspace_2/wanxiangdakao'
for root, dirs, files in os.walk(d):
    if '.git' in root:
        continue
    for f in files:
        fp = os.path.join(root, f)
        try:
            rel = os.path.relpath(fp, d)
        except:
            rel = fp
        has_non_ascii = any(ord(ch) > 127 for ch in f)
        has_special = any(ch in f for ch in '<>|*?\"')
        if has_non_ascii or has_special:
            print(repr(rel))
