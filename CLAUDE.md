# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build the project
./gradlew build

# Clean build
./gradlew clean build

# Run the app (requires connected device or emulator)
./gradlew installDebug

# Run unit tests
./gradlew test

# Run a specific test class
./gradlew test --tests com.canerture.e_commerce_app.ExampleUnitTest

# Run instrumented tests
./gradlew connectedAndroidTest

# Generate debug APK
./gradlew assembleDebug

# Generate release APK
./gradlew assembleRelease
```

## Architecture Overview

This is an Android e-commerce application built with **MVVM + Clean Architecture** principles.

### Layer Structure

**Data Layer** (`data/`)
- `source/remote/`: Retrofit API service for product data
- `source/local/`: Room database for favorites persistence
- `repository/`: Repository implementations that coordinate remote and local data sources
- `model/`: Data models (POJOs)

**Domain Layer** (`domain/`)
- `repository/`: Repository interfaces (abstraction)
- `datasource/`: Data source interfaces (local and remote abstractions)
- `usecase/`: Single-responsibility use cases organized by feature (bag, category, favorite, login, product)

**Presentation Layer** (`presentation/`)
- Each screen is a separate package containing:
  - Fragment (View)
  - ViewModel
  - Adapters (if needed)
- Single Activity (`MainActivity`) with Navigation Component
- Bottom navigation visible on main screens, hidden on auth/splash/success screens

### Key Architectural Patterns

**Dependency Injection (Hilt)**
- All modules in `di/` package:
  - `RetrofitModule`: Provides ProductService with BASE_URL from Constants
  - `RoomDBModule`: Provides FavoritesRoomDB and ProductFavoriteDAO
  - `FirebaseModule`: Firebase Auth and Firestore
  - `AuthModule`: Authenticator implementation
  - `DataSourceModule`: Local and Remote data source implementations
  - `RepositoryModule`: Repository implementations
  - `CoroutineModule`: Coroutine dispatchers

**Data Flow**
1. Fragment observes LiveData from ViewModel
2. ViewModel calls UseCases
3. UseCases call Repository interfaces
4. Repository implementations coordinate LocalDataSource and RemoteDataSource
5. Data sources handle Room DB or Retrofit API calls

**State Management**
- Uses `Resource<T>` sealed class for state (Loading, Success, Error)
- LiveData for reactive UI updates
- Coroutines with viewModelScope for async operations

### Navigation

Two navigation graphs:
- `login_graph.xml`: Auth flow (Splash → SignIn → SignUp → ForgotPassword)
- `main_graph.xml`: Main app flow with bottom nav (Home, Favorites, Bag, Profile) + Detail/Payment/Success screens

Search accessed via FloatingActionButton from any main screen.

### Data Sources

**Remote (Firebase Firestore + REST API)**
- Products fetched from `https://api.canerture.com/ecommerce/`
- Shopping bag stored in Firestore collection for user `canerture`
- Firebase Auth for authentication

**Local (Room Database)**
- Database: `favorites_database.db`
- Single table for favorite products via `ProductFavoriteDAO`
- Favorites are merged with remote product data (Repository layer checks if product title is in favorites list)

### Important Implementation Details

**Favorites Logic**
- Favorites stored locally in Room
- When fetching products, repository queries `localDataSource.getFavoritesNamesList()` to check which products are favorited
- Product model has `isFavorite` boolean set by comparing title with favorites list

**Sale Price Calculation**
- Products with `saleState == 1` get 15% discount: `salePrice = (price * 85) / 100`
- Applied in repository layer when mapping remote data to domain Product model

**Search Results**
- Search returns max 4 products (hardcoded in `ProductsRepositoryImpl.searchProduct()` with loop `for (i in 0..3)`)

**View Binding**
- Uses custom `viewBinding` delegate from `common/delegate/` package
- DataBinding enabled in addition to ViewBinding

## Common Utilities

**Extensions** (`common/Extensions.kt`)
- `View.visible()`, `View.invisible()`, `View.gone()`: Visibility helpers
- `View.showSnackbar()`: Shows 1.5s Snackbar
- `TextInputEditText.isNullorEmpty()`, `isValidEmail()`: Validation with TextInputLayout error handling
- `AutoCompleteTextView.checkMonthYear()`: Payment form validation
- `View.dpToPx()`: Convert dp to pixels

**Constants** (`common/Constants.kt`)
- `BASE_URL`: API endpoint
- `USER`: Hardcoded user "canerture" for Firestore queries
- Firestore field names and collection paths

## Firebase Configuration

Requires `google-services.json` in `app/` directory for Firebase integration (Auth and Firestore).

## Testing

Only placeholder unit test exists (`ExampleUnitTest.kt`). No meaningful test coverage.