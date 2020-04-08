from geopy.geocoders import Nominatim
from geopy.extra.rate_limiter import RateLimiter
import json
import folium

geolocator = Nominatim(user_agent="specify_your_app_name_here")
location = geolocator.geocode("Teatrul National Vasile Alecsandri Iasi ")
f = (location.longitude,location.latitude)
f = open("locatii_input.txt", "r") # locatia nu trebuie sa aiba virgula
data= []
for i in f:
    location = geolocator.geocode(i)
    data.append((location.latitude,location.longitude,location.address))

print(data)
with open("locatii_output.txt", 'w') as outfile:
    json.dump(data, outfile)
   
geocode = RateLimiter(locator.geocode, min_delay_seconds=1)
map1 = folium.Map(
    location=[47.1585,27.6014],
    tiles='cartodbpositron',
    zoom_start=12,
)
dt.apply(lambda row:folium.CircleMarker(location=[row["latitude"], row["longitude"]]).add_to(map1), axis=1)
map1
