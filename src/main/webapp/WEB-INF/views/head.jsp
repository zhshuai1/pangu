<nav class="navbar navbar-inverse">
    <div class="container-fluid navbar-content">
        <div>
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><%=LocaleUtil.localize(locale, "SEPISM_041")%>
            </a>
        </div>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav navbar-nav">
                <li class="active" id="nav-index">
                    <a href="/"><%=LocaleUtil.localize(locale, "INDEX_042")%><span class="sr-only">(current)</span></a>
                </li>
                <li id="nav-submit">
                    <a href="/viewQuestionnaires"><%=LocaleUtil.localize(locale, "ANSWER_043")%>
                    </a>
                </li>
                <li id="nav-account">
                    <a href="/account"><%=LocaleUtil.localize(locale, "ACCOUNT_045")%>
                    </a>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="input-group">
                    <input class="form-control" type="text"
                           placeholder="<%=LocaleUtil.localize(locale,"SEARCH_044")%>">
                    <span class="input-group-addon btn btn-default">
                        <span class="glyphicon glyphicon-search"></span>
                    </span>
                </div>
            </form>
        </div>
    </div>
</nav>
