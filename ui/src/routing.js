import React from 'react'
import { Route, Switch, withRouter } from 'react-router-dom'
import { UserContext } from './usercontext';
import SignedInView from './components/signedinview';
import SignInView from './views/signInView';

import HomeView from './views/home.view';
import QuestionView from './views/question/question.view';
import BlogListView from './views/bloglist.view';
import UsersView from './views/users.view';
import SearchView from './views/search.view';
import FaqView from './views/faq.view';
import CreateQuestionView from './views/question/createquestion.view';
import PageNotFoundView from './views/pagenotfound.view';
import * as User from './models/user';
import Loader from './components/utils/loader';

class Routing extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            currentTheme: 'no-theme'
        };

        this.GoogleAuth = null;
        this.GoogleUser = null;
    }

    setTheme(theme) {
        if (!theme) {
            theme = 'light';
        }
        let styleElement = document.getElementById(theme);
        let allStyles = [...document.getElementsByClassName('styles')];

        allStyles.map((style) => {
            style.disabled = true;
        });
        styleElement.disabled = false;

        this.setState({
            currentTheme: theme
        });
    }

    componentDidUpdate(prevProps, prevState) {
        const contextTheme = this.context.state.theme;
        if (contextTheme && prevState.currentTheme !== contextTheme) {
            this.setTheme(contextTheme);
        }
    }

    componentWillMount() {
        this.setTheme(this.context.state.theme);
        gapi.load('auth2', () => {
            gapi.auth2.init({
                client_id: window.googleClientId
            });
            this.GoogleAuth = gapi.auth2.getAuthInstance();
            this.setupSignInListener();

            this.GoogleAuth.then(() => {
                this.GoogleUser = this.GoogleAuth.currentUser.get();
                const googleId  = this.GoogleUser.getId();
                if (!googleId) {
                    this.setState({
                        loaded: true
                    });
                }
            });
        });
    }

    setupSignInListener() {
        this.GoogleAuth.isSignedIn.listen((isSignedIn) => {
            if (isSignedIn) {
                const token = this.GoogleUser.getAuthResponse().id_token;
                this.signInUser(token);
                return;
            }
            User.signOut().finally(() => {
                this.context.setState({
                            user: null,
                            token: null
                        });
                this.props.history.push('/');
            });
        });
    }

    signInUser(token) {
        User.signIn(token).then((user) => {
            this.updateUserInContext(user, token);
        })
            .catch(() => {
                this.updateUserInContext();
            });
    }

    updateUserInContext(user = null, token = null) {
        this.setState({
            loaded: true
        });

        this.context.setState({
            user: user,
            token: token
        });
    }

    signIn() {
        this.setState({
            loaded: false
        });
        this.GoogleAuth.signIn().catch(() => {
            this.setState({
                loaded: true
            });
        });
    }

    render() {
        if (!this.state.loaded) {
            return <Loader fillPage />
        }

        if (!this.context.state.user) {
            return (
                <div className="view">
                    <SignInView onSignIn={this.signIn.bind(this)} />
                </div>
            )
        }

        return (
            <SignedInView>
                <Switch>
                    <Route exact path="/"
                           render={() => <HomeView />} />
                    <Route path="/questions"
                           render={() => <QuestionsView />} />
                    <Route exact path="/question/:id"
                           render={() => <QuestionView />} />
                    <Route path="/bloglist/:id?"
                           render={() => <BlogListView />} />
                    <Route path="/users/:userId?"
                           render={() => <UsersView />} />
                    <Route path="/create/:type/:id?"
                           render={() => <CreateQuestionView />} />
                    <Route path="/search/:query?"
                           render={() => <SearchView />} />
                    <Route path="/faq"
                           render={() => <FaqView />} />
                    <Route render={() => <PageNotFoundView />} />
                </Switch>
            </SignedInView>
        );
    }
}

export default withRouter(Routing);

Routing.contextType = UserContext;
