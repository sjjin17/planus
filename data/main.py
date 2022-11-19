import json
import pandas as pd
from urllib.request import urlopen
from sqlalchemy import create_engine
from dotenv import load_dotenv
import os
import time
import ssl

def api():
    load_dotenv()

    API_SERVICE_KEY = os.environ.get('API_SERVICE_KEY')

    context = ssl._create_unverified_context()

    url = "https://apis.data.go.kr/B551011/KorService/areaBasedList?numOfRows=50000&pageNo=1&MobileOS=ETC&MobileApp=test&serviceKey="+API_SERVICE_KEY+"&_type=json"
    response = urlopen(url, context=context)
    json_api = response.read().decode("utf-8")
    apiEnd = time.time()
    print("API done in ",apiEnd-start)
    result_dict = json.loads(json_api)
    rows = result_dict['response']['body']['items']['item']
    print("json parsing done in ",time.time()-apiEnd)

    rowList = []
    nameList = []
    columnList = []
    needColumnList = ["addr1", "contenttypeid", "firstimage", "mapx", "mapy", "title"]
    contentDic = {"12": "관광지", "14": "문화시설", "15": "15", "25": "여행코스", "28": "레포츠", "32": "숙박", "38": "쇼핑", "39": "음식점"}
    rowsLen = len(rows)
    column = ["address", "content_type", "img_url", "lng", "lat", "place"]
    for i in range(0, rowsLen):
        columns = rows[i]
        for j in columns:
            if i == 0:
                nameList.append(j)
            if j in needColumnList:
                eachColumn = columns[j]
                if j == "contenttypeid":
                    eachColumn = contentDic.get(eachColumn)
                columnList.append(eachColumn)
        rowList.append(columnList)
        columnList = []

    result = pd.DataFrame(rowList, columns=column)

    result['lng'] = pd.to_numeric(result['lng'])
    result['lat'] = pd.to_numeric(result['lat'])

    drop = result[result.lng < 132]
    drop = drop[drop.lng > 124]
    drop = drop[drop.lat < 43]
    drop = drop[drop.lat > 33]
    drop = drop[drop.content_type != '15']
    drop = drop[drop.address != '']
    drop = drop[drop.img_url != '']
    drop = drop[drop.place != '']

    # areacode -> 지역(도)로 변환?
    data = drop
    DB_USER_NAME=os.environ.get('DB_USER_NAME')
    DB_PASSWORD=os.environ.get('DB_PASSWORD')
    DB_URL=os.environ.get('DB_URL')

    db_connection_str = "mysql+pymysql://"+DB_USER_NAME+":"+DB_PASSWORD+"@"+DB_URL+"/planus"
    engine = create_engine(db_connection_str, encoding='utf8')
    conn = engine.connect()
    conn.execute('TRUNCATE TABLE Recommend')
    data.to_sql(name="Recommend", con=engine, if_exists='append', index=False)

    conn.close()

if __name__ == '__main__':
    start = time.time()
    api()
    print("recommend Update in ",time.time()-start)

