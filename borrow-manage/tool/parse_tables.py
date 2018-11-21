#! /usr/bin/env python

import glob
import re

r = re.compile(r'TABLE\s+\`(.*?)\`')

for x in glob.glob('*.sql'):
    with open(x) as f:
        for line in f:
            m = r.search(line)
            if m is None: continue
            x = m.groups()[0].split('_')
            print('<table tableName="%s" domainObjectName="%s"/>' % (m.groups()[0], 
                ''.join([z.capitalize() for z in x])))



