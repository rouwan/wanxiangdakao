import os

# Check what CDN images the old entry page used
base = r'd:\workspace\workspace_2\wanxiangdakao\万象大考整理\wxdk备份\user\dakao'
old_entry = os.path.join(base, 'src', 'pages', 'entry', 'index.vue')

print('=== Old entry page image references ===')
with open(old_entry, 'r', encoding='utf-8', errors='replace') as f:
    content = f.read()
    for line in content.split('\n'):
        if 'src=' in line or 'background' in line or 'url(' in line:
            line = line.strip()
            print(line[:200])

print('\n=== Old sprite SCSS files - background images ===')
for scss_file in ['et-img-map.scss', '3v3-img-map.scss', 'icon.scss', 'public.scss', 'hybird.scss']:
    fp = os.path.join(base, 'src', 'scss', scss_file)
    if os.path.exists(fp):
        with open(fp, 'r', encoding='utf-8', errors='replace') as f:
            content = f.read()
        for line in content.split('\n'):
            if 'background-image' in line or 'url(' in line:
                print(f'  {scss_file}: {line.strip()[:200]}')

print('\n=== New project static files ===')
new_static = r'd:\workspace\workspace_2\wanxiangdakao\万象大考v3\dakao\static'
for root, dirs, files in os.walk(new_static):
    for f in sorted(files):
        fp = os.path.join(root, f)
        rel = fp.replace(new_static, '')
        print(f'  {rel} ({os.path.getsize(fp):,}b)')
