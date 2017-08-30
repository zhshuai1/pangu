<nav class="navbar navbar-inverse bg-inverse navbar-toggleable-md">
    <div class="container">
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="/"><%=LocaleUtil.localize(locale, "SEPISM_041")%>
        </a>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/"><%=LocaleUtil.localize(locale, "INDEX_042")%> <span class="sr-only">(current)
            </span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/viewQuestionnaires"><%=LocaleUtil.localize(locale, "ANSWER_043")%>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/account"><%=LocaleUtil.localize(locale, "ACCOUNT_045")%>
                    </a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="text"
                       placeholder="<%=LocaleUtil.localize(locale,"SEARCH_044")%>">
                <button class="btn btn-outline-success my-2 my-sm-0"
                        type="submit"><%=LocaleUtil.localize(locale, "SEARCH_044")%>
                </button>
            </form>
        </div>
    </div>
</nav>