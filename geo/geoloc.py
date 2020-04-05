from geopy.geocoders import Nominatim
import json

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