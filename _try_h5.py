import subprocess
import os
import time

dakao_dir = r'd:\workspace\workspace_2\wanxiangdakao\万象大考v3\dakao'

# First check if uni-h5 is available
print('Checking H5 support...')
npm_ls = subprocess.run(
    'npm.cmd ls @dcloudio/uni-h5 2>&1',
    shell=True, capture_output=True, text=True, cwd=dakao_dir
)
print(npm_ls.stdout[:500])
if npm_ls.stderr:
    print(npm_ls.stderr[:300])

# If missing, install it
if 'UNMET DEPENDENCY' in npm_ls.stdout or 'missing:' in npm_ls.stdout.lower():
    print('\nInstalling @dcloudio/uni-h5...')
    result = subprocess.run(
        'npm.cmd install --legacy-peer-deps @dcloudio/uni-h5@3.0.0-alpha-5010320260611001 2>&1',
        shell=True, capture_output=True, text=True, timeout=120, cwd=dakao_dir
    )
    print(result.stdout[-500:] if result.stdout else '')
    if result.stderr:
        print(result.stderr[-500:])

# Also check/install uni-h5-vue that caused issues
result = subprocess.run(
    'npm.cmd install --legacy-peer-deps @dcloudio/uni-h5-vue@3.0.0-alpha-5010220260604001 @dcloudio/uni-h5-vite@3.0.0-alpha-5010220260604001 2>&1',
    shell=True, capture_output=True, text=True, timeout=120, cwd=dakao_dir
)
print(result.stdout[-300:] if result.stdout else '')

# Now try dev:h5
print('\n=== Starting H5 dev server ===')
log_file = os.path.join(os.path.dirname(os.path.abspath(__file__)), '_dev_output.log')
with open(log_file, 'w', encoding='utf-8') as log:
    proc = subprocess.Popen(
        'npm.cmd run dev:h5 2>&1',
        shell=True,
        stdout=log,
        stderr=subprocess.STDOUT,
        cwd=dakao_dir
    )

# Wait for server to start
for i in range(30):
    time.sleep(2)
    with open(log_file, 'r', encoding='utf-8', errors='replace') as log:
        content = log.read()
    if 'Local:' in content or 'localhost' in content or 'http://' in content:
        # Extract URL
        for line in content.split('\n'):
            if 'http://' in line or 'Local:' in line:
                print(f'\n*** SERVER STARTED ***')
                print(line.strip())
        break
    if 'error' in content.lower() and i > 3:
        print(f'ERROR at {i*2}s')
        for line in content.split('\n')[-10:]:
            if line.strip():
                print(line[:250])
        break
else:
    print('Timeout. Last output:')
    with open(log_file, 'r', encoding='utf-8', errors='replace') as log:
        content = log.read()
    for line in content.split('\n')[-15:]:
        if line.strip():
            print(line[:250])

if proc.poll() is None:
    print('\nServer is running!')
    proc.terminate()
