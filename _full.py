import subprocess, os, shutil, stat, time

fe = r'd:\workspace\workspace_2\wanxiangdakao\v3\前台前端'
dist = os.path.join(fe, 'dist')

def onerror(fn, p, info):
    os.chmod(p, stat.S_IWRITE)
    fn(p)

# Force delete
for i in range(5):
    try:
        if os.path.exists(dist):
            shutil.rmtree(dist, onerror=onerror)
        print('dist deleted')
        break
    except:
        time.sleep(2)
else:
    print('dist still locked! Make sure DevTools project is closed.')
    exit(1)

# Clean build
r = subprocess.run([r'D:\Java\node10\node.exe', 'build/build.js'],
    stdout=subprocess.PIPE, stderr=subprocess.STDOUT, timeout=300, cwd=fe)
o = r.stdout.decode('utf-8', errors='replace') if r.stdout else ''
errs = [l for l in o.split('\n') if 'Error' in l]
wxmls = sum(1 for r2,d2,fs in os.walk(dist) for f2 in fs if f2.endswith('.wxml'))
print(f'Build: exit={r.returncode} errs={len(errs)} wxml={wxmls}')
if errs:
    for e in errs[:5]: print(e[:300])

# Start dev server
p = subprocess.Popen([r'D:\Java\node10\node.exe', 'build/dev-server.js'],
    stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, cwd=fe)
print(f'Dev server: PID={p.pid}')
print('Done. Re-import v3/前台前端/ in DevTools.')
