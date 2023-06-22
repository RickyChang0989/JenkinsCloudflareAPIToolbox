import getopt
import json
import sys
import requests

def purge_cloudflare_cache_by_hosts(identifier, api_key, hosts):
    body = {}
    body['hosts'] = hosts
    body_json = json.dumps(body)
    r = requests.post(
        'https://api.cloudflare.com/client/v4/zones/{}/purge_cache'.format(identifier), 
        headers= {
            "Authorization": "Bearer {}".format(api_key),
            "Content-Type": "application/json"
        },
        data = body_json
    )

    print(body)
    print(r.content)


def main(argv):
    api_key = ""
    hosts = []
    identifier = ""
    try:
        opts, args = getopt.getopt(argv,"t:k:i:",["hosts=", "key=","identifier="])
    except getopt.GetoptError:
        print('purge_cloudflare_cache_by_hosts.py -k <api_key> -i <identifier> -t <hosts>')
        sys.exit(2)

    for opt, arg in opts:
        if opt == '-h':
            print('purge_cloudflare_cache_by_hosts.py -k <api_key> -i <identifier> -t <hosts>')
            sys.exit()
        elif opt in ("-k", "--key"):
            api_key = arg
        elif opt in ("-i", "--identifier"):
            identifier = arg
        elif opt in ("-t", "--hosts"):
            hosts = arg.split(',')

    purge_cloudflare_cache_by_hosts(identifier, api_key, hosts)

main(sys.argv[1:])