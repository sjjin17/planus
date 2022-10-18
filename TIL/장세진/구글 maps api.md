### 구글 maps api

##### 1. 마커로 위치  표시

- `google.maps.Marker()` 

  - 마커를 놓고 싶은 위치의 위경도와 라벨, 생성한 지도 객체를 넘긴다.

    ```javascript
    // 구글 지도 보여주기
    window.initMap = function () {
      const map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 37.5400456, lng: 126.9921017 },
        zoom: 10,
      });
    };
    
    // 마커로 위치 표시
      const malls = [
        { label: "C", name: "코엑스몰", lat: 37.5115557, lng: 127.0595261 },
        { label: "G", name: "고투몰", lat: 37.5062379, lng: 127.0050378 },
        { label: "D", name: "동대문시장", lat: 37.566596, lng: 127.007702 },
        { label: "I", name: "IFC몰", lat: 37.5251644, lng: 126.9255491 },
        { label: "L", name: "롯데월드타워몰", lat: 37.5125585, lng: 127.1025353 },
        { label: "M", name: "명동지하상가", lat: 37.563692, lng: 126.9822107 },
        { label: "T", name: "타임스퀘어", lat: 37.5173108, lng: 126.9033793 },
      ];
      malls.forEach(({ label, name, lat, lng }) => {
        const marker = new google.maps.Marker({
          position: { lat, lng },
          label,
          map: map,
        });
      });
    ```

    

- 마커 클릭시 정보 제공

  - `google.maps.InfoWindow()` 생성자로 정보창을 하나 생성 => 각 마커의 클릭 이벤트가 발생하면 정보창이 뜨도록 설정

    ```javascript
    const infowindow = new google.maps.InfoWindow();
    
    // 정보창 생성
        marker.addListener("click", () => {
          map.panTo(marker.position);  // 마커 클릭시 마커가 있는 위치로 지도 중심이 이동
          infowindow.setContent(name);
          infowindow.open({
            anchor: marker,
            map,
          });
        });
    
    
    ```

    

##### 2. 장소 검색

- 사용자의 위치 또는 검색 문자열을 기준으로 장소 목록 반환
- 장소 세부정보도 반환 (사용자 리뷰를 포함한 장소에 대한 자세한 정보)
  - 장소 사진에도 엑세스 가능
- 자동 완성 가능
  - 추천 검색어 반환



###### 2-1. Find place 응답

```json
{
  "candidates":
    [
      {
        "formatted_address": "140 George St, The Rocks NSW 2000, Australia",
        "geometry":
          {
            "location": { "lat": -33.8599358, "lng": 151.2090295 },
            "viewport":
              {
                "northeast":
                  { "lat": -33.85824377010728, "lng": 151.2104386798927 },
                "southwest":
                  { "lat": -33.86094342989272, "lng": 151.2077390201073 },
              },
          },
        "name": "Museum of Contemporary Art Australia",
        "opening_hours": { "open_now": false },
        "rating": 4.4,
      },
    ],
  "status": "OK",
}
```





[참고] 지도 자체는 커스텀이 되는 것 같은데 검색은 안되는 것 같음

위의 json 응답 처럼 리스트로 받아서 보여주는게 좋을 듯