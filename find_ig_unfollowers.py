import json

# Instructions:
#  - Edit the directory to lead to your desktop (or another you choose).
#  - Make sure the followers and following files are named appropriately and in the correct directory.
#     - (and json format, but maybe json format is not necessary)
#  - Open the json file that will be created in the choosen directory.

# Load followers list
with open('C:/Users/your_user_directory/Desktop/followers.json', 'r') as f:
    followers_data = json.load(f)
    followers = {item['string_list_data'][0]['value'] for item in followers_data}

# Load following list
with open('C:/Users/your_user_directory/Desktop/following.json', 'r') as f:
    following_data = json.load(f)
    following = {item['string_list_data'][0]['value'] for item in following_data['relationships_following']}

# Find people you follow who don't follow you back
not_following_back = following - followers

# Format result as JSON
not_following_back_json = [{"username": user} for user in not_following_back]

# Save to a JSON file
with open('C:/Users/your_user_directory/Desktop/not_following_back.json', 'w') as f:
    json.dump(not_following_back_json, f, indent=2)

print("List of people who don't follow you back has been saved as 'not_following_back.json'")
