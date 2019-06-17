import React from 'react';
import { NavLink } from 'react-router-dom';
import moment from 'moment';

import Certificate from '../utils/certificate';
import Coins from '../utils/coins';
import Trophy from '../utils/trophy';

class QuestionCard extends React.Component {
    constructor(props) {
        super(props);
    }

    getTime() {
        return moment(this.props.question.createdAt).fromNow();
    }

    getState() {
        if (this.props.question.answerAccepted) {
            return 'answered';
        }

        return 'unanswered';
    }

    printCertificate() {
        const hasAccepted = this.props.question.answerAccepted;
        return <Certificate active={hasAccepted} />;
    }

    printTrophy() {
        const hasEnoughVotes = this.props.question.votes > 20;
        return <Trophy active={hasEnoughVotes} />;
    }

    getClasses() {
        let classes = 'question-card';
        if (this.props.small) {
            classes = `${classes} small`;
        }
        return classes;
    }

    renderTags() {
        if (this.props.hideTags) {
            return null;
        }

        return (
            <div className="tags">
                <div className="tag">JavaScript</div>
                <div className="tag">ReactJs</div>
                <div className="tag">Frontend</div>
            </div>
        );
    }

    render() {
        return (
            <div className={`${this.getClasses()} ${this.getState()}`}>
                {/*<Coins amount={this.props.question.bounty} />*/}
                <div className="marks">
                    {this.printCertificate()}
                    {this.printTrophy()}
                </div>
                <div className="body">
                    <div className="title">
                        <NavLink className="title" to={`/question/${this.props.question.id}`}>
                            {this.props.question.title}
                        </NavLink>
                    </div>
                    <div className="user">
                        {this.props.question.createdBy}, {this.getTime()}
                    </div>
                </div>
            </div>
        );
    }
}

QuestionCard.defaultProps = {
    user: {},
    question: {},
    small: false,
    hideTags: false
};

export default QuestionCard;