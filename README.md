# Flickster

‘Flickster’ is an android app used to view trailers. This is a read-only movie listing app using the Movie Database API.

Submitted by: Amod Pradeep Samant

Time spent: 10-12 hours spent in total

User Stories

The following user stories are completed:

- [x] User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.

- [x] Lists should be fully optimized for performance using the ViewHolder pattern to cache view lookups within the adapter.

- [x] Views should be responsive for both landscape/portrait mode.

Advanced Stories:

- [x] Display a nice default placeholder graphic for each image during loading (read more about Picasso)

- [x] Improve the user interface through styling and coloring

- [x] For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous ListViews and use different ViewHolder layout files for popular movies and less popular ones.

- [x] Stretch: Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.

- [x] Stretch: Allow video posts to be played in full-screen using the YouTubePlayerView
    When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
    Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.

- [x] Stretch: Add a play icon overlay to popular movies to indicate that the movie can be played.

- [x] Stretch: Apply the popular ButterKnife annotation library to reduce view boilerplate.

- [x] Stretch: Add a rounded corners for the images using the Picasso transformations.

- [x] Stretch: Replace android-async-http network client with the popular OkHttp networking library for all TheMovieDB   API calls.

Additional Stories:
- [x] Popular movies are denoted with a star in the list

- [x] Button onclick uses a different drawable from a selector to add a dim effect in the click visualization

- [x] Flickster app has a new icon

- [x] Used Picasso library functions to get the correct size of the images in the views
