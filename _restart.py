import subprocess, os

script_dir = os.path.dirname(os.path.abspath(__file__))
jar = os.path.join(script_dir, '万象大考整理', 'wxdk备份', 'user', 'vientianetestappendapi', 'target', 'vientianetestapi.jar')
out = os.path.join(script_dir, '_out.txt')

print(f'Starting: {jar}')
with open(out, 'w', encoding='utf-8') as fh:
    p = subprocess.Popen(
        ['java', '-jar', jar],
        stdout=fh,
        stderr=subprocess.STDOUT,
        cwd=script_dir
    )
    print(f'Backend started, PID={p.pid}')
    print(f'Logs: {out}')
