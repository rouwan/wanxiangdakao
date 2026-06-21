import subprocess, os, time

script_dir = 'd:/workspace/workspace_2/wanxiangdakao'
java = 'D:/Java/jdk-1.8/bin/java'
project = os.path.join(script_dir, '万象大考整理', 'wxdk备份', 'user', 'vientianetestappendapi')
jar = os.path.join(project, 'target', 'vientianetestapi.jar')
log = os.path.join(script_dir, '_out.txt')

# Clear old log
with open(log, 'wb') as fh:
    fh.write(b'# Backend log (UTF-8)\n')

with open(log, 'ab') as fh:
    p = subprocess.Popen([
        java,
        '-Dfile.encoding=UTF-8',
        '-Xss2m',
        '-jar', jar
    ], stdout=fh, stderr=subprocess.STDOUT, cwd=script_dir)

print(f'PID={p.pid}')
for i in range(20):
    time.sleep(1)
    r = subprocess.run(['netstat', '-ano'], capture_output=True, text=True)
    if any('8008' in l and 'LISTENING' in l for l in r.stdout.split('\n')):
        print(f'READY ({i+1}s)')
        break
else:
    print('TIMEOUT')
