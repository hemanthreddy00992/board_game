# Standard Library Imports
from chardet.universaldetector import UniversalDetector
import math
from time import sleep
# Third Party Imports
from bs4 import BeautifulSoup
import pandas as pd
import requests
from yaml import FullLoader, load
# Local Imports
from config import SETTINGS


def test_encoding(file_name):
    detector = UniversalDetector()
    n = 0
    with open(file_name, 'rb') as f:
        for line in f:
            n += 1
            detector.feed(line)
            print("{}".format(n))
            if detector.done:
                 break
        detector.close()
    r = detector.result
    print("Detected encoding %s with confidence %s" % (r['encoding'], r['confidence']))


def scrape_categories(headers):
    tags = []
    r = requests.get(url=SETTINGS['category_url'], headers=headers)
    if r.status_code == 200:
        c = r.content
        soup = BeautifulSoup(c, features="html.parser")
        tds = soup.find_all('td', {'width': '50%'})
        for td in tds:
            tag = td.findChildren("a", recursive=False)[0]
            tags.append(tag.contents[0])
        print("done categories")
    else:
        print("Failed to connect to categories")


def scrape_mechanics(headers):
    tags = []
    r = requests.get(url=SETTINGS['mechanic_url'], headers=headers)
    if r.status_code == 200:
        c = r.content
        soup = BeautifulSoup(c, features="html.parser")
        tds = soup.find_all('td', {'width': '50%'})
        for td in tds:
            tag = td.findChildren("a", recursive=False)[0]
            tags.append(tag.contents[0])
        print("done mechanics")
    else:
        print("Failed to connect to categories")

# ==============================================================
# Main
# ==============================================================
if __name__ == '__main__':
    headers = {'User-Agent': SETTINGS['user_agent']}

    # test_encoding(SETTINGS['data_file'])
    scrape_categories(headers)
    scrape_mechanics(headers)

    """
    with open(SETTINGS['data_file'], 'r', encoding='utf-8') as f:
        df = pd.read_csv(f, encoding='utf-8', sep=',', error_bad_lines=False)


    page_counter = 0
    for index, row in df.iterrows():
        page_counter += 1
        if type(row['description']) != type(1.0):
            continue
        # If no description in df, attempt to get it
        if math.isnan(row['description']):
            # increment page counter
            r = requests.get(url=row['bgg_url'], headers=headers)
            if r.status_code == 200:
                print("Connected! Extracting {} - {}".format(row['names'], page_counter))
                # Wait
                sleep(1)
                c = r.content
                soup = BeautifulSoup(c, features="html.parser")
                # If error, skip page
                try:
                    # Find the body content
                    body_content = soup.find('meta', {'name': 'description'})
                    desc = body_content.attrs['content']
                    # Add it to description col
                    df.iloc[index, df.columns.get_loc('description')] = desc
                except:
                    print("Couldn't find element to connect on {} - {}. Skipping....".format(row['names'], page_counter))
                    df.iloc[index, df.columns.get_loc('description')] = 'PLACEHOLDER'
            else:
                print("Failed to connect on {} - {}. Skipping....".format(row['names'], page_counter))
                df.iloc[index, df.columns.get_loc('description')] = 'PLACEHOLDER'
        # Save DF progress every 1 min
        if page_counter % 60 == 0:
            print("="*20)
            print("UPDATED DF")
            print("=" * 20)
            df.to_csv(SETTINGS['out_file'])
    """

    print("done!")
