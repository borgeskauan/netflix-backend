# Netflix Clone – Backend

This is the backend service for a Netflix-like web application, built using **Spring Boot** and **Java 21**. It powers features such as movie search, playlists, reviews, and user subscription plans. The backend is intentionally kept simple, using **in-memory H2 database** and no external infrastructure dependencies, making it easy to run locally.

---

## Tech Stack

- **Java 21**
- **Spring Boot**
- **Integration with TMDB** (The Movie Database) API
- **H2 Database** (in-memory)
- **YAML-based config** for plan definitions

---

## Features

### Authentication

- Register and log in users (with optional upgrade from guest to full user)
- Guest users supported for anonymous browsing

### Movie Data (via TMDB Proxy)

- Fetch popular movies
- Search movies
- View detailed information
- View movies similar to a particular movie
- All the movies comes with user metadata, such as whether the movie has been watched before or not

### Playlists

- Create custom playlists
- Add/remove movies from playlists
- System-defined playlists like **Favorites** and **Watch Later**
- Plan-based limits on how many playlists a user can create

### Reviews

- Draft and submit one review per movie
- View all user reviews per movie
- Permissions based on subscription level (e.g. only Premium users can reply)

### Subscription Plans

Three subscription tiers:

- **Free**: basic access with viewing-only features
- **Pro**: allows writing reviews, more playlists, and extra data like similar movies
- **Elite**: full access to all features including advanced search, review replies, and unlimited playlists

Plans are defined in a `plans-config.yml` file and loaded into the application at startup.

---

## Project Structure

- **`application.yml`**  
  Main Spring Boot configuration, including database, server, and TMDB API access.

- **`plans-config.yml`**  
  Defines all available subscription plans and their associated features.

- **`/auth` endpoints**  
  Handle user registration, login, and guest account creation.

- **`/movies` endpoints**  
  Serve movie listings, search, and recommendations based on TMDB API.

- **`/playlists` endpoints**  
  CRUD operations on user playlists and handling system-defined ones like Favorites.

- **`/reviews` endpoints**  
  Manage review drafts, submissions, and list reviews for movies.

- **`/plans` endpoints**  
  Allow users to view or change their current plan and see available options.

---

## Running the Backend

You can run the backend with the following command:

```bash
./mvnw spring-boot:run
```

Make sure to set the following environment variables (or define them in `.env.properties`):

- `DATABASE_URL` – Optional for H2; leave it empty or use default.
- `TMDB_API_URL` – Base URL for the TMDB API (e.g., `https://api.themoviedb.org/3`)
- `TMDB_API_KEY` – Your TMDB API key.

---

## Testing

Unit tests are not yet implemented but are part of the roadmap.

---

## Roadmap Highlights

The backend currently supports all main features. Future goals include:

- Adding unit tests with 90% coverage
- Support for review replies and likes
- Support for Youtube trailers
- Advanced movie search filters and sorting
- Internationalization support
- Deleting playlists

---

## API Structure

The backend is documented using OpenAPI, generated automatically by Spring Boot, and follows a clear path-based structure:

- `/auth/*` – Auth operations
- `/movies/*` – Movie data
- `/reviews/*` – Review drafts and submissions
- `/playlists/*` – Playlist management
- `/plans/*` – Subscription and plan management

All of the endpoints are served under `/netflix`. The OpenAPI documentation can be access at `/v3/api-docs` or `/swagger-ui/index.html`.

## Notes

- The backend uses simple UUID-based authentication rather than JWT for simplicity
- All endpoints require a user ID parameter to enforce user context
- Subscription plan features are enforced at the API level
- Also check out our (front end)[https://github.com/borgeskauan/netflix-frontend] that integrates with this service
