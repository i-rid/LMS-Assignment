# LMS Assignment
## 📂 MVVV - Project Structure

Here’s an overview of the project structure:
```plaintext
.
└── lmsassignment
├── data
│   ├── model
│   │   ├── BattingResponse.kt
│   │   ├── BowlingResponse.kt
│   │   ├── JsonParsing.kt
│   │   ├── SquadResponse.kt
│   │   └── SummaryResponse.kt
│   └── remote
│       └── api
│           └── ApiService.kt
├── MyApp.kt
├── utils
│   ├── AppUiState.kt
│   ├── Const.kt
│   ├── SharedPrefManager.kt
│   └── Views.kt
├── view
│   ├── AuthActivity.kt
│   ├── bottom_nav
│   │   ├── HomeFragment.kt
│   │   ├── MyLMSFragment.kt
│   │   └── ProfileFragment.kt
│   ├── child_tabs
│   │   ├── adapter
│   │   │   ├── BatingAdapter.kt
│   │   │   ├── BowlingAdapter.kt
│   │   │   ├── ChildTabAdapter.kt
│   │   │   ├── SquadAdapter.kt
│   │   │   ├── TopPlayersAdapter.kt
│   │   │   └── VideoAdapter.kt
│   │   ├── AFragment.kt
│   │   ├── BFragment.kt
│   │   └── CFragment.kt
│   ├── HomeActivity.kt
│   └── parent_tabs
│       ├── adapter
│       │   └── ParentTabAdapter.kt
│       ├── FeaturedFragment.kt
│       └── ProFragment.kt
└── view_model
└── LMSViewModel.kt
