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
            rel = repr(fp)
        # Look for files with 'if' or 'else' or 'j==' in the name
        if 'if ' in f or 'else' in f or 'j==' in f or '==' in f:
            print(f'FOUND: {repr(rel)}')
        # Also look for files with very unusual characters
        for ch in f:
            code = ord(ch)
            if code < 32 or (code > 126 and code < 160):
                print(f'CONTROL/UNUSUAL: {repr(rel)}  (char U+{code:04X})')
                break

print('Scan complete.')
