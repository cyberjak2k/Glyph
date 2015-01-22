package jp.org.example.geckour.glyph;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shaper.db";
    public static final String TABLE_NAME1 = "shapers";
    public static final String TABLE_NAME2 = "sets";
    public static final int DB_VERSION = 2;

    private String[][] SHAPERS = {
            {"ABANDON", "6,4,0,2,9,8"},
            {"ADAPT", "10,2,0,1"},
            {"ADVANCE", "5,3,9"},
            {"AGAIN", "9,3,2,0,4,1"},
            {"ALL", "5,6,7,8,9,10,5"},
            {"ANSWER", "3,4,1,0"},
            {"ATTACK", "9,3,5,4,7"},
            {"AVOID", "10,5,4,6,1"},
            {"BARRIER", "5,0,1,7"},
            {"BEGIN", "5,2,8,1"},
            {"BEING", "8,2,3,4,1,8"},
            {"BODY", "0,3,4,0"},
            {"BREATHE", "10,3,0,4,6"},
            {"CAPTURE", "6,1,0,2,9,8"},
            {"CHANGE", "2,0,8,1"},
            {"CHAOS", "9,10,5,6,4,0,2,8"},
            {"CLEAR", "5,0,8"},
            {"CLEAR ALL", "5,0,8,9,10,5,6,7,8"},
            {"COMPLEX", "4,3,0,2"},
            {"CONFLICT", "9,3,2,1,4,7"},
            {"CONSEQUENCE", "10,3,2,1,7"},
            {"CONTEMPLATE", "5,6,7,8,2,3,0,4"},
            {"CONTRACT", "1,4,7"},
            {"COURAGE", "9,3,2,1"},
            {"CREATE", "9,2,0,4,6"},
            {"CREATIVITY", "0,8,2,10,3,0"},
            {"MIND", "2,9,10,3,0,1,7,6,4"},
            {"DANGER", "5,3,0,8"},
            {"DATA", "5,4,0,2,8"},
            {"DEFEND", "10,2,8,1,6"},
            {"DESTINATION", "6,7,8"},
            {"DESTINY", "3,0,4,1,2,8"},
            {"DESTROY", "10,3,0,1,7"},
            {"DETERIORATE", "3,0,2,9"},
            {"DIE", "9,2,0,1,7"},
            {"DIFFICULT", "2,0,1,4,6"},
            {"DISCOVER", "6,7,8,9"},
            {"DISTANCE", "5,10,9"},
            {"EASY", "4,0,2,8"},
            {"END", "8,0,5,6,1,8"},
            {"ENLIGHTENED_A", "8,7,6,5,3,0,4,3"},
            {"ENLIGHTENED_B", "9,8,7,6,5,3,0,4,3"},
            {"EQUAL", "2,3,4,1"},
            {"ESCAPE", "5,6,4,3,2"},
            {"EVOLUTION", "5,0,3,2"},
            {"FAILURE", "5,0,4,1"},
            {"FEAR", "3,4,1,6"},
            {"FOLLOW", "5,4,6,7"},
            {"FORGET", "2,9"},
            {"FUTURE", "6,4,1,7"},
            {"GAIN", "10,2"},
            {"GOVERNMENT", "10,3,2,1,4,6"},
            {"GROW", "9,3,2"},
            {"HARM", "7,1,0,3,5,4,0"},
            {"HARMONY", "0,1,8,2,0,4,5,3,0"},
            {"HAVE", "1,0,2,8"},
            {"HELP", "10,3,0,2,1"},
            {"END", "8,0,5,6,1,8"},
            {"HIDE", "3,4,6,1,2"},
            {"I", "8,3,4,8"},
            {"IGNORE", "1,7"},
            {"IMPERFECT", "0,3,2,0,4,2"},
            {"IMPROVE", "6,4,0,1"},
            {"IMPURE", "8,0,3,2,0"},
            {"INTERRUPT", "5,0,3,10,9,2,0,8"},
            {"JOURNEY", "6,4,0,3,10,9,8"},
            {"KNOWLEDGE", "8,3,0,4,8"},
            {"LEAD", "5,10,9,2,8"},
            {"LEGACY", "9,2,3,10,5,6,4,1,7"},
            {"LESS", "3,0,4"},
            {"LIBERATE", "9,3,0,4,6,5"},
            {"LIE", "2,3,0,1,4,0"},
            {"LIVE AGAIN", "9,3,2,0,4,6"},
            {"LOSE", "6,1"},
            {"MESSAGE", "9,3,0,1,6"},
            {"IDEA", "8,2,3,0,8"},
            {"MORE", "2,0,1"},
            {"MYSTERY", "10,3,5,4,3,2"},
            {"NATURE", "9,2,3,4,1,7"},
            {"NEW", "4,1,7"},
            {"NO", "3,4,1"},
            {"NOURISH", "8,9,2,0,8"},
            {"OLD", "10,3,2"},
            {"OPEN", "8,2,1,8"},
            {"OPEN ALL", "8,2,1,8,9,10,5,6,7,8"},
            {"OPENING", "10,3,4,6,7,1,2,9,10"},
            {"PAST", "10,3,2,9"},
            {"PATH", "5,0,2,9"},
            {"PERFECTION", "5,0,2,9,8,7,1,0"},
            {"PERSPECTIVE", "9,2,0,4,5,3,0,1,7"},
            {"POTENTIAL", "5,0,1,7,6"},
            {"PRESENCE", "2,3,0,4,1,2,8,1"},
            {"PRESENT", "3,2,1,4"},
            {"PURE", "5,0,4,1,0"},
            {"PURSUE", "10,3,5,4"},
            {"CHASE", "5,0,3,2,9"},
            {"QUESTION", "5,4,3,2"},
            {"REACT", "4,3,0,1,7"},
            {"REBEL", "10,2,0,4,6,7"},
            {"RECHARGE", "0,3,10,5,0"},
            {"RESISTANCE_A", "4,3,5,0,8,2"},
            {"RESISTANCE_B", "4,3,5,0,8,1"},
            {"RESTRAINT", "10,3,0,1,7,8"},
            {"RETREAT", "5,4,7"},
            {"SAFETY", "9,3,4,7"},
            {"SAVE", "2,0,1,6"},
            {"SEE", "5,3"},
            {"SEEK", "0,4,3,2,1"},
            {"SELF", "9,8,7"},
            {"SEPARATE", "10,3,2,0,4,1,7"},
            {"SHAPERS", "9,2,3,5,4,1,7"},
            {"SHARE", "7,1,2,9,8"},
            {"SIMPLE", "2,1"},
            {"SOUL", "8,0,4,1,8"},
            {"STABILITY", "9,2,1,7"},
            {"STRONG", "2,3,4,1,2"},
            {"TOGETHER", "9,2,0,4,3,0"},
            {"TRUTH", "3,0,1,4,0,2,3"},
            {"USE", "0,1,6"},
            {"VICTORY", "8,3,5,4,8"},
            {"WANT", "9,2,8,1"},
            {"WE", "3,4,8"},
            {"WEAK", "10,3,4,1"},
            {"WORTH", "10,2,0,1,6"},
            {"XM", "2,3,4,1,0,2"},
            {"YOU", "5,2,1,5"}
    };
    
    private String[][] SETS = {
            {"5", "ADVANCE,GOVERNMENT,PURSUE,SHAPERS,PATH", ",CIVILIZATION,,,"},
            {"5", "ADVANCE,GOVERNMENT,PURSUE,SHAPERS,TRUTH", ",CIVILIZATION,,,"},
            {"5", "ANSWER,QUESTION,DISCOVER,DIFFICULT,TRUTH", null},
            {"5", "ATTACK,BEING,GOVERNMENT,XM,MESSAGE", ",HUMAN,CIVILIZATION,,"},
            {"5", "ATTACK,PURE,FUTURE,BEING,GOVERNMENT", ",,,HUMAN,CIVILIZATION"},
            {"5", "ATTACK,PURE,FUTURE,NO,ATTACK", ",,,INSIDE,WAR"},
            {"5", "ATTACK,SEPARATE,PATH,END,JOURNEY", null},
            {"5", "AVOID,CHAOS,AVOID,SHAPERS,LIE", null},
            {"5", "AVOID,CHAOS,RECHARGE,POTENTIAL,ATTACK", ",,REPAIR,,WAR"},
            {"5", "AVOID,PERFECTION,STABILITY,BEING,SELF", ",,,HUMAN,"},
            {"5", "BREATHE,NO,XM,LOSE,SELF", ",INSIDE,,,"},
            {"5", "CAPTURE,OPENING,DEFEND,OPENING,COURAGE", ",PORTAL,,PORTAL,"},
            {"5", "CHANGE,SEPARATE,PAST,END,JOURNEY", null},
            {"5", "CHAOS,ATTACK,CONFLICT,DISCOVER,HARMONY", null},
            {"5", "CHAOS,PERFECTION,STABILITY,BEING,SELF", ",,,HUMAN,"},
            {"5", "CLEAR,MIND,LIBERATE,BARRIER,BODY", null},
            {"5", "CLEAR ALL,MIND,PAST,PRESENT,FUTURE", null},
            {"5", "CLEAR ALL,MIND,LIBERATE,BARRIER,BODY", null},
            {"5", "CONTEMPLATE,FUTURE,NO,SHAPERS,PATH", ",,NOT,,"},
            {"5", "CONTEMPLATE,RESTRAINT,DISCOVER,MORE,COURAGE", null},
            {"5", "COURAGE,ATTACK,SHAPERS,OPENING,TOGETHER", ",,,PORTAL,"},
            {"5", "COURAGE,DESTROY,SHAPERS,OPENING,TOGETHER", ",,,PORTAL,"},
            {"5", "CREATE,PURE,FUTURE,BEING,GOVERNMENT", ",,,HUMAN,CIVILIZATION"},
            {"5", "CREATE,PURE,FUTURE,NO,ATTACK", ",,,NOT,WAR"},
            {"5", "CREATE,SEPARATE,PATH,END,JOURNEY", null},
            {"5", "DEFEND,DESTINY,DEFEND,BEING,GOVERNMENT", ",,,HUMAN,CIVILIZATION"},
            {"5", "DEFEND,BEING,GOVERNMENT,OPENING,MESSAGE", ",HUMAN,CIVILIZATION,PORTAL,"},
            {"5", "DEFEND,BEING,GOVERNMENT,SHAPERS,LIE", ",HUMAN,CIVILIZATION,,"},
            {"5", "DEFEND,BEING,GOVERNMENT,SHAPERS,OPENING", ",HUMAN,CIVILIZATION,,PORTAL"},
            {"5", "DEFEND,BEING,GOVERNMENT,XM,MESSAGE", ",HUMAN,CIVILIZATION,,"},
            {"5", "DESTROY,BEING,GOVERNMENT,SHAPERS,LIE", ",HUMAN,CIVILIZATION,,"},
            {"5", "DESTROY,GOVERNMENT,END,CONFLICT,ATTACK", ",CIVILIZATION,,,WAR"},
            {"5", "DESTROY,GOVERNMENT,PRESENT,CONFLICT,ATTACK", ",CIVILIZATION,NOW,,WAR"},
            {"5", "DESTROY,LIE,NO,GAIN,SOUL", ",,INSIDE,,"},
            {"5", "DISTANCE,I,AVOID,BEING,LIE", ",SELF,,HUMAN,"},
            {"5", "EASY,PAST,FUTURE,FOLLOW,SHAPERS", null},
            {"5", "ESCAPE,BODY,JOURNEY,DISTANCE,PRESENT", ",,,OUTSIDE,NOW"},
            {"5", "FORGET,ATTACK,SEE,DISTANCE,HARMONY", ",,,OUTSIDE,"},
            {"5", "FORGET,ATTACK,GAIN,DISTANCE,HARMONY", ",WAR,,OUTSIDE,"},
            {"5", "FORGET,PAST,SEE,PRESENT,DANGER", null},
            {"5", "FORGET,ATTACK,SEE,DISTANCE,HARMONY", ",WAR,,,"},
            {"5", "GAIN,TRUTH,OPEN,BEING,SOUL", ",,,HUMAN,"},
            {"5", "HARM,EVOLUTION,PURSUE,MORE,ATTACK", ",PROGRESS,,,WAR"},
            {"5", "HELP,ENLIGHTENED_A,CAPTURE,ALL,OPENING", ",ENLIGHTENMENT,,,PORTAL"},
            {"5", "HELP,BEING,GOVERNMENT,PURSUE,DESTINY", ",HUMAN,CIVILIZATION,,"},
            {"5", "HELP,RESISTANCE_A,CAPTURE,ALL,OPENING", ",RESISTANCE,,,PORTAL"},
            {"5", "HIDE,RESISTANCE_A,ADVANCE,STRONG,TOGETHER", ",RESISTANCE,,,"},
            {"5", "BEING,NO,TOGETHER,GOVERNMENT,DETERIORATE", "HUMAN,NOT,CIVILIZATION,,"},
            {"5", "BEING,SHAPERS,TOGETHER,CREATE,DESTINY", "HUMAN,,,,"},
            {"5", "IMPERFECT,TRUTH,OPEN,COMPLEX,ANSWER", null},
            {"5", "IMPERFECT,XM,MESSAGE,BEING,CHAOS", ",,,HUMAN,"},
            {"5", "IMPROVE,MIND,IMPROVE,COURAGE,CHANGE", null},
            {"5", "NO,MIND,NO,SOUL,HARMONY", "INSIDE,,INSIDE,,"},
            {"5", "LIBERATE,OPENING,LIBERATE,BEING,MIND", ",PORTAL,,HUMAN,"},
            {"5", "LIBERATE,SELF,LIBERATE,BEING,GOVERNMENT", ",,,HUMAN,CIVILIZATION"},
            {"5", "BREATHE,NO,XM,LOSE,SELF", "LIVE,INSIDE,,,"},
            {"5", "LOSE,SHAPERS,MESSAGE,GAIN,CHAOS", null},
            {"5", "MIND,BODY,SOUL,PURE,BEING", ",,,HUMAN"},
            {"5", "MORE,DATA,GAIN,OPENING,ADVANCE", ",,,PORTAL,"},
            {"5", "OLD,NATURE,LESS,STRONG,PRESENT", ",,,,NOW"},
            {"5", "DISTANCE,SELF,AVOID,BEING,LIE", "OUTSIDE,,,HUMAN,"},
            {"5", "PAST,BARRIER,CREATE,FUTURE,JOURNEY", null},
            {"5", "PAST,CHAOS,CREATE,FUTURE,HARMONY", null},
            {"5", "PAST,PATH,CREATE,FUTURE,JOURNEY", null},
            {"5", "OPENING,ATTACK,DANGER,PURSUE,SAFETY", "PORTAL,,,,"},
            {"5", "OPENING,BARRIER,DEFEND,BEING,SHAPERS", "PORTAL,,,HUMAN,"},
            {"5", "OPENING,CREATE,DANGER,PURSUE,SAFETY", "PORTAL,,,,"},
            {"5", "OPENING,IMPROVE,BEING,FUTURE,GOVERNMENT", "PORTAL,,HUMAN,,CIVILIZATION"},
            {"5", "OPENING,POTENTIAL,HELP,BEING,FUTURE", "PORTAL,,,HUMAN,"},
            {"5", "PRESENT,CHAOS,CREATE,FUTURE,GOVERNMENT", ",,,,CIVILIZATION"},
            {"5", "PURSUE,CONFLICT,ADVANCE,ATTACK,CHAOS", ",,,WAR,"},
            {"5", "PURSUE,CONFLICT,ATTACK,ADVANCE,CHAOS", ",,WAR,,"},
            {"5", "PURSUE,PATH,DISTANCE,SHAPERS,LIE", ",,OUTSIDE,,"},
            {"5", "QUESTION,BEING,GOVERNMENT,DESTROY,OPENING", ",HUMAN,CIVILIZATION,,PORTAL"},
            {"5", "QUESTION,LESS,FORGET,ALL,LIE", null},
            {"5", "REACT,REBEL,QUESTION,SHAPERS,LIE", null},
            {"5", "REBEL,MIND,EVOLUTION,DESTINY,HARMONY", ",IDEA,,,"},
            {"5", "REBEL,MIND,EVOLUTION,DESTINY,PRESENT", ",IDEA,,,NOW"},
            {"5", "RECHARGE,NO,RECHARGE,BEING,SOUL", "REPAIR,INSIDE,REPAIR,HUMAN,"},
            {"5", "RECHARGE,PRESENT,RECHARGE,BEING,SOUL", "REPAIR,,REPAIR,HUMAN,"},
            {"5", "RECHARGE,SOUL,LESS,BEING,HARM", "REPAIR,,,HUMAN,"},
            {"5", "SAVE,BEING,GOVERNMENT,DESTROY,OPENING", ",HUMAN,CIVILIZATION,,PORTAL"},
            {"5", "SEEK,DESTINY,CREATE,PURE,FUTURE", "SEARCH,,,,"},
            {"5", "SEPARATE,MIND,BODY,DISCOVER,ENLIGHTENED_A", ",,,,ENLIGHTENMENT"},
            {"5", "SEPARATE,TRUTH,LIE,SHAPERS,FUTURE", null},
            {"5", "SHAPERS,LEAD,BEING,COMPLEX,JOURNEY", ",,HUMAN,,"},
            {"5", "SHAPERS,OPENING,DATA,CREATE,CHAOS", ",PORTAL,,,"},
            {"5", "SHAPERS,OPENING,MESSAGE,DESTROY,GOVERNMENT", ",PORTAL,,,CIVILIZATION"},
            {"5", "SHAPERS,SEE,COMPLEX,PATH,DESTINY", null},
            {"5", "SHAPERS,WANT,BEING,MIND,FUTURE", ",,HUMAN,,"},
            {"5", "SIMPLE,OLD,TRUTH,JOURNEY,NO", ",,,,INSIDE"},
            {"5", "SIMPLE,TRUTH,FORGET,EASY,EVOLUTION", ",,,,SUCCESS"},
            {"5", "SIMPLE,TRUTH,SHAPERS,DESTROY,GOVERNMENT", ",,,,CIVILIZATION"},
            {"5", "STABILITY,STRONG,TOGETHER,DEFEND,RESISTANCE_A", "STAY,,,,RESISTANCE"},
            {"5", "STRONG,TOGETHER,ATTACK,TOGETHER,DESTINY", null},
            {"5", "USE,MIND,USE,COURAGE,CHANGE", null},
            {"5", "USE,RESTRAINT,FOLLOW,EASY,PATH", null},
            {"5", "WANT,TRUTH,PURSUE,DIFFICULT,PATH", null},
            {"5", "WEAK,BEING,DESTINY,DESTROY,GOVERNMENT", ",HUMAN,,,CIVILIZATION"},
            {"5", "XM,CREATE,COMPLEX,BEING,DESTINY", ",,,HUMAN,"},
            {"5", "XM,PAST,FUTURE,DESTINY,HARMONY", null},
            {"5", "XM,PATH,FUTURE,JOURNEY,HARMONY", null},
            //#4
            {"4", "ADVANCE,FUTURE,NO,ATTACK", ",NOT,WAR"},
            {"4", "ADVANCE,GOVERNMENT,AGAIN,FAILURE", ",CIVILIZATION,REPEAT,"},
            {"4", "ALL,CHAOS,NO,BODY", ",,INSIDE,"},
            {"4", "ATTACK,GOVERNMENT,AGAIN,FAILURE", ",CIVILIZATION,REPEAT,"},
            {"4", "ATTACK,ENLIGHTENED_A,PURSUE,RESISTANCE_A", ",ENLIGHTENMENT,,RESISTANCE"},
            {"4", "ATTACK,FUTURE,CHANGE,DESTINY", null},
            {"4", "ATTACK,RESISTANCE_A,PURSUE,ENLIGHTENED_A", ",RESISTANCE,,ENLIGHTENMENT"},
            {"4", "ATTACK,WEAK,SHAPERS,LIE", null},
            {"4", "AVOID,XM,MESSAGE,LIE", null},
            {"4", "BREATHE,AGAIN,JOURNEY,AGAIN", null},
            {"4", "BREATHE,NATURE,PERFECTION,HARMONY", null},
            {"4", "CAPTURE,FEAR,DISCOVER,COURAGE", null},
            {"4", "CHANGE,BODY,IMPROVE,BEING", ",,,HUMAN"},
            {"4", "CHANGE,FUTURE,CAPTURE,DESTINY", null},
            {"4", "CHANGE,BEING,POTENTIAL,USE", ",HUMAN,,"},
            {"4", "CHANGE,SIMPLE,BEING,FUTURE", ",,HUMAN,"},
            {"4", "CHAOS,BARRIER,SHAPERS,OPENING", ",,,PORTAL"},
            {"4", "CHAOS,DESTROY,SHAPERS,OPENING", ",,,PORTAL"},
            {"4", "CLEAR,MIND,OPEN,MIND", null},
            {"4", "CLEAR ALL,OPEN ALL,DISCOVER,TRUTH", null},
            {"4", "CLEAR ALL,OPEN ALL,GAIN,TRUTH", null},
            {"4", "CLEAR ALL,OPEN,MIND,BEGIN", null},
            {"4", "COMPLEX,SHAPERS,GOVERNMENT,STRONG", ",,CIVILIZATION,"},
            {"4", "CONTEMPLATE,COMPLEX,SHAPERS,GOVERNMENT", ",,,CIVILIZATION"},
            {"4", "CONTEMPLATE,COMPLEX,SHAPERS,TRUTH", null},
            {"4", "CONTEMPLATE,SELF,PATH,TRUTH", null},
            {"4", "COURAGE,ATTACK,SHAPERS,FUTURE", ",WAR,,"},
            {"4", "CREATE,DISTANCE,IMPURE,PATH", null},
            {"4", "CREATE,FUTURE,CHANGE,DESTINY", null},
            {"4", "CREATE,FUTURE,NO,ATTACK", ",,INSIDE,WAR"},
            {"4", "DEFEND,MESSAGE,ANSWER,MIND", ",,,IDEA"},
            {"4", "DESTROY,COMPLEX,SHAPERS,LIE", null},
            {"4", "DESTROY,DESTINY,BEING,LIE", ",,HUMAN,"},
            {"4", "DETERIORATE,BEING,WEAK,REBEL", ",HUMAN,,"},
            {"4", "DISCOVER,PERFECTION,SAFETY,ALL", null},
            {"4", "DISTANCE,YOU,MIND,MORE", null},
            {"4", "END,JOURNEY,DISCOVER,DESTINY", null},
            {"4", "ESCAPE,SIMPLE,BEING,FUTURE", ",,HUMAN,"},
            {"4", "FOLLOW,SHAPERS,OPENING,MESSAGE", ",,PORTAL,"},
            {"4", "FORGET,CONFLICT,OPEN,ATTACK", ",,ACCEPT,WAR"},
            {"4", "GAIN,OPENING,ATTACK,WEAK", ",PORTAL,,"},
            {"4", "HARMONY,PATH,NOURISH,PRESENT", null},
            {"4", "HELP,GAIN,CREATE,PURSUE", null},
            {"4", "HELP,SHAPERS,CREATE,FUTURE", null},
            {"4", "HIDE,IMPURE,BEING,MIND", ",,HUMAN,THOUGHT"},
            {"4", "BEING,HAVE,IMPURE,GOVERNMENT", "HUMAN,,,CIVILIZATION"},
            {"4", "BEING,PAST,PRESENT,FUTURE", "HUMAN,,,"},
            {"4", "BEING,SOUL,STRONG,PURE", "HUMAN,,,"},
            {"4", "IGNORE,BEING,CHAOS,LIE", ",HUMAN,,"},
            {"4", "IMPROVE,BODY,MIND,SOUL", null},
            {"4", "IMPROVE,BODY,PURSUE,JOURNEY", null},
            {"4", "IMPROVE,MIND,BODY,NO", ",,,INSIDE"},
            {"4", "IMPROVE,MIND,JOURNEY,NO", ",,,INSIDE"},
            {"4", "NO,MIND,JOURNEY,PERFECTION", "INSIDE,,,"},
            {"4", "NO,MIND,SOUL,HARMONY", "INSIDE,,,"},
            {"4", "JOURNEY,NO,IMPROVE,SOUL", ",INSIDE,,"},
            {"4", "LEAD,BODY,MIND,SOUL", null},
            {"4", "LEAD,PURSUE,REACT,DEFEND", null},
            {"4", "LESS,CHAOS,MORE,STABILITY", null},
            {"4", "LESS,MIND,MORE,SOUL", null},
            {"4", "LESS,SOUL,MORE,MIND", null},
            {"4", "LESS,TRUTH,MORE,CHAOS", null},
            {"4", "LIBERATE,XM,OPENING,TOGETHER", ",,PORTAL,"},
            {"4", "BREATHE,AGAIN,JOURNEY,AGAIN", "LIVE,,,"},
            {"4", "BREATHE,NATURE,PERFECTION,HARMONY", "LIVE,,BALANCE,"},
            {"4", "LOSE,DANGER,GAIN,SAFETY", null},
            {"4", "MORE,MIND,LESS,SOUL", ",,,SPIRIT"},
            {"4", "NO,MIND,JOURNEY,PERFECTION", "NOT,,,"},
            {"4", "NOURISH,XM,CREATE,MIND", ",,,THOUGHT"},
            {"4", "OPEN,CHAOS,NO,BODY", ",,INSIDE,"},
            {"4", "OPEN ALL,CLEAR ALL,DISCOVER,TRUTH", null},
            {"4", "PAST,AGAIN,PRESENT,AGAIN", null},
            {"4", "PATH,RESTRAINT,STRONG,SAFETY", null},
            {"4", "HARMONY,PATH,NOURISH,FUTURE", "PEACE,,,"},
            {"4", "HARMONY,PATH,NOURISH,PRESENT", "PEACE,,,NOW"},
            {"4", "PERFECTION,PERFECTION,SAFETY,ALL", ",BALANCE,,"},
            {"4", "OPENING,CHANGE,GOVERNMENT,END", "PORTAL,,CIVILIZATION,"},
            {"4", "OPENING,DIE,GOVERNMENT,DIE", "PORTAL,,CIVILIZATION,"},
            {"4", "OPENING,HAVE,TRUTH,DATA", "PORTAL,,,"},
            {"4", "OPENING,POTENTIAL,CHANGE,FUTURE", "PORTAL,,,"},
            {"4", "PRESENT,MIND,JOURNEY,PERFECTION", null},
            {"4", "QUESTION,TRUTH,GAIN,FUTURE", null},
            {"4", "QUESTION,YOU,IMPURE,GOVERNMENT", ",,,CIVILIZATION"},
            {"4", "RESISTANCE_A,DEFEND,SHAPERS,DANGER", "RESISTANCE,,,"},
            {"4", "RESTRAINT,FEAR,AVOID,DANGER", null},
            {"4", "RESTRAINT,PATH,GAIN,HARMONY", null},
            {"4", "SAVE,BEING,POTENTIAL,USE", ",HUMAN,,"},
            {"4", "SEE,TRUTH,SEE,FUTURE", null},
            {"4", "SEEK,DATA,DISCOVER,PATH", "SEARCH,,,"},
            {"4", "SEEK,TRUTH,SAVE,GOVERNMENT", "SEARCH,,,CIVILIZATION"},
            {"4", "SEEK,XM,SAVE,OPENING", "SEARCH,,,PORTAL"},
            {"4", "SEEK,TRUTH,SEE,FUTURE", null},
            {"4", "SEPARATE,WEAK,IGNORE,TRUTH", null},
            {"4", "SHAPERS,AVOID,PURE,MIND", ",,,THOUGHT"},
            {"4", "SHAPERS,CHAOS,PURE,HARM", null},
            {"4", "SHAPERS,GAIN,POTENTIAL,EVOLUTION", null},
            {"4", "SHAPERS,HAVE,STRONG,PATH", null},
            {"4", "SHAPERS,LOSE,POTENTIAL,EVOLUTION", null},
            {"4", "SHAPERS,MESSAGE,END,GOVERNMENT", ",,,CIVILIZATION"},
            {"4", "SHAPERS,MIND,COMPLEX,HARMONY", null},
            {"4", "SHAPERS,PAST,PRESENT,FUTURE", null},
            {"4", "SHAPERS,OPENING,MIND,RESTRAINT", ",PORTAL,,"},
            {"4", "SHAPERS,SEE,POTENTIAL,EVOLUTION", null},
            {"4", "SIMPLE,GOVERNMENT,IMPURE,WEAK", ",CIVILIZATION,,"},
            {"4", "SIMPLE,MESSAGE,COMPLEX,IDEA", null},
            {"4", "SIMPLE,TRUTH,BREATHE,NATURE", ",,LIVE,"},
            {"4", "SOUL,BEING,REBEL,DIE", ",HUMAN,,"},
            {"4", "STABILITY,PURE,BREATHE,KNOWLEDGE", ",,LIVE,"},
            {"4", "STABILITY,TOGETHER,DEFEND,TRUTH", "STAY,HUMAN,,"},
            {"4", "STRONG,MIND,PURSUE,TRUTH", ",IDEA,,"},
            {"4", "STRONG,RESISTANCE_A,CAPTURE,OPENING", ",RESISTANCE,,PORTAL"},
            {"4", "STRONG,TOGETHER,AVOID,ATTACK", ",,,WAR"},
            {"4", "AVOID,DEFEND,SHAPERS,DANGER", "STRUGGLE,,,"},
            {"4", "AVOID,IMPROVE,BEING,SOUL", "STRUGGLE,,HUMAN,"},
            {"4", "TOGETHER,DISCOVER,HARMONY,EQUAL", null},
            {"4", "TRUTH,MIND,DISCOVER,XM", ",IDEA,,"},
            {"4", "XM,DIE,CHAOS,BREATHE", ",,,LIVE"},
            {"4", "XM,HAVE,MIND,HARMONY", null},
            {"4", "XM,HAVE,MIND,JOURNEY", null},
            {"4", "YOU,DESTINY,NO,EASY", ",,NOT,"},
            //#3
            {"3", "OPEN,BEING,WEAK", "ACCEPT,HUMAN,"},
            {"3", "ADVANCE,BEING,ENLIGHTENED_A", ",HUMAN,ENLIGHTENED"},
            {"3", "ADVANCE,BEING,RESISTANCE_A", ",HUMAN,RESISTANCE"},
            {"3", "ADVANCE,PURE,TRUTH", null},
            {"3", "AGAIN,JOURNEY,DISTANCE", ",,OUTSIDE"},
            {"3", "ALL,GOVERNMENT,CHAOS", ",CIVILIZATION,"},
            {"3", "ALL,XM,LIBERATE", null},
            {"3", "ATTACK,CREATE,DANGER", null},
            {"3", "ATTACK,DESTROY,FUTURE", null},
            {"3", "ATTACK,DIFFICULT,FUTURE", null},
            {"3", "ATTACK,SHAPERS,CHAOS", null},
            {"3", "ATTACK,SHAPERS,EVOLUTION", null},
            {"3", "ATTACK,SHAPERS,OPENING", ",,PORTAL"},
            {"3", "AVOID,ATTACK,CHAOS", null},
            {"3", "AVOID,CHAOS,SOUL", null},
            {"3", "AVOID,COMPLEX,CONFLICT", null},
            {"3", "AVOID,COMPLEX,SOUL", null},
            {"3", "AVOID,COMPLEX,TRUTH", null},
            {"3", "AVOID,DESTINY,LIE", null},
            {"3", "AVOID,IMPURE,EVOLUTION", null},
            {"3", "AVOID,PURE,CHAOS", null},
            {"3", "AVOID,ATTACK,CHAOS", ",WAR,"},
            {"3", "ANSWER,AGAIN,AVOID", ",REPEAT,STRUGGLE"},
            {"3", "CAPTURE,SHAPERS,OPENING", ",,PORTAL"},
            {"3", "CAPTURE,XM,OPENING", ",,PORTAL"},
            {"3", "CHANGE,BEING,FUTURE", ",HUMAN,"},
            {"3", "GOVERNMENT,ATTACK,CHAOS", "CIVILIZATION,WAR,"},
            {"3", "COMPLEX,JOURNEY,FUTURE", null},
            {"3", "CONTEMPLATE,JOURNEY,DISTANCE", ",,OUTSIDE"},
            {"3", "CONTEMPLATE,POTENTIAL,PERFECTION", null},
            {"3", "CONTEMPLATE,POTENTIAL,JOURNEY", null},
            {"3", "COURAGE,DESTINY,REBEL", null},
            {"3", "CREATE,FUTURE,JOURNEY", null},
            {"3", "DANGER,CHANGE,PAST", null},
            {"3", "DEFEND,SEEK,SAFETY", null},
            {"3", "DESTROY,DESTINY,BARRIER", null},
            {"3", "DESTROY,DIFFICULT,BARRIER", null},
            {"3", "DESTROY,GOVERNMENT,DANGER", ",CIVILIZATION,"},
            {"3", "DESTROY,IMPURE,TRUTH", null},
            {"3", "DESTROY,WEAK,GOVERNMENT", ",,CIVILIZATION"},
            {"3", "DETERIORATE,ADVANCE,PRESENT", null},
            {"3", "DISCOVER,HARMONY,EQUAL", null},
            {"3", "DISCOVER,OPENING,TRUTH", ",PORTAL,"},
            {"3", "DISCOVER,PURE,TRUTH", null},
            {"3", "DISCOVER,RESISTANCE_A,TRUTH", ",RESISTANCE,"},
            {"3", "DISCOVER,SAFETY,GOVERNMENT", ",,CIVILIZATION"},
            {"3", "DISCOVER,SHAPERS,GOVERNMENT", ",,CIVILIZATION"},
            {"3", "DISCOVER,SHAPERS,ENLIGHTENED_A", ",,ENLIGHTENMENT"},
            {"3", "DISCOVER,SHAPERS,LIE", null},
            {"3", "DISCOVER,SHAPERS,MESSAGE", null},
            {"3", "ESCAPE,IMPURE,EVOLUTION", null},
            {"3", "ESCAPE,IMPURE,FUTURE", null},
            {"3", "ESCAPE,IMPURE,TRUTH", null},
            {"3", "ESCAPE,OPENING,HARM", ",PORTAL,"},
            {"3", "ESCAPE,SHAPERS,HARM", null},
            {"3", "ESCAPE,SHAPERS,HARMONY", null},
            {"3", "FEAR,CHAOS,XM", null},
            {"3", "FEAR,COMPLEX,XM", null},
            {"3", "FOLLOW,PURE,JOURNEY", null},
            {"3", "FUTURE,EQUAL,PAST", null},
            {"3", "GAIN,GOVERNMENT,HARMONY", ",CIVILIZATION,PEACE"},
            {"3", "GAIN,FUTURE,ESCAPE", null},
            {"3", "HARM,DANGER,AVOID", null},
            {"3", "HARMONY,STABILITY,FUTURE", null},
            {"3", "HIDE,JOURNEY,TRUTH", null},
            {"3", "HIDE,PATH,FUTURE", null},
            {"3", "BEING,GAIN,SAFETY", "HUMAN,,"},
            {"3", "IMPROVE,ADVANCE,PRESENT", null},
            {"3", "IMPROVE,FUTURE,TOGETHER", null},
            {"3", "IMPROVE,BEING,SHAPERS", ",HUMAN,"},
            {"3", "NO,MIND,FUTURE", "INSIDE,,"},
            {"3", "NO,XM,TRUTH", "INSIDE,,"},
            {"3", "JOURNEY,NO,SOUL", ",INSIDE,"},
            {"3", "LEAD,ENLIGHTENED_A,GOVERNMENT", ",ENLIGHTENMENT,CIVILIZATION"},
            {"3", "LEAD,RESISTANCE_A,QUESTION", ",RESISTANCE,"},
            {"3", "LIBERATE,BEING,FUTURE", ",HUMAN,"},
            {"3", "LIBERATE,OPENING,POTENTIAL", ",PORTAL,"},
            {"3", "LOSE,ATTACK,RETREAT", ",WAR,"},
            {"3", "MIND,BODY,BREATHE", ",,LIVE"},
            {"3", "MIND,EQUAL,TRUTH", null},
            {"3", "MIND,OPEN,BREATHE", ",,LIVE"},
            {"3", "NATURE,PURE,DEFEND", null},
            {"3", "NOURISH,MIND,JOURNEY", null},
            {"3", "NOURISH,XM,OPENING", ",,PORTAL"},
            {"3", "OPEN,BEING,WEAK", ",HUMAN,"},
            {"3", "OPEN ALL,OPENING,EVOLUTION", ",PORTAL,SUCCESS"},
            {"3", "OPEN ALL,SIMPLE,TRUTH", null},
            {"3", "PAST,EQUAL,FUTURE", null},
            {"3", "PAST,HARMONY,DIFFICULT", null},
            {"3", "PAST,HARMONY,DIFFICULT", ",PEACE,"},
            {"3", "PAST,PRESENT,FUTURE", null},
            {"3", "PATH,HARMONY,DIFFICULT", ",PEACE,"},
            {"3", "HARMONY,SIMPLE,JOURNEY", "PEACE,,"},
            {"3", "HARMONY,STABILITY,FUTURE", "PEACE,,"},
            {"3", "PERFECTION,PAST,HARMONY", ",,PEACE"},
            {"3", "POTENTIAL,TRUTH,HARMONY", null},
            {"3", "POTENTIAL,XM,ATTACK", null},
            {"3", "POTENTIAL,XM,HARMONY", ",,PEACE"},
            {"3", "PURSUE,COMPLEX,TRUTH", null},
            {"3", "PURSUE,PURE,BODY", null},
            {"3", "QUESTION,CONFLICT,DATA", null},
            {"3", "QUESTION,HIDE,TRUTH", null},
            {"3", "QUESTION,BEING,TRUTH", ",HUMAN,"},
            {"3", "QUESTION,SHAPERS,CHAOS", null},
            {"3", "REACT,IMPURE,GOVERNMENT", ",,CIVILIZATION"},
            {"3", "REACT,PURE,TRUTH", null},
            {"3", "RECHARGE,NATURE,PERFECTION", "REPAIR,,BALANCE"},
            {"3", "AGAIN,JOURNEY,DISTANCE", "REPEAT,,OUTSIDE"},
            {"3", "AGAIN,SEEK,SAFETY", "REPEAT,SEARCH,"},
            {"3", "SEEK,XM,OPENING", "SEARCH,,PORTAL"},
            {"3", "SEE,TRUTH,PRESENT", ",,NOW"},
            {"3", "SEPARATE,FUTURE,EVOLUTION", null},
            {"3", "SEPARATE,FUTURE,PURSUE", null},
            {"3", "TOGETHER,PURE,JOURNEY", null},
            {"3", "TOGETHER,PURSUE,SAFETY", null},
            {"3", "TRUTH,NOURISH,SOUL", null},
            {"3", "WANT,TRUTH,PRESENT", ",,NOW"},
            {"3", "ATTACK,ATTACK,CHAOS", "WAR,,"},
            {"3", "ATTACK,CREATE,DANGER", "WAR,,"},
            {"3", "ATTACK,DESTROY,FUTURE", "WAR,,"},
            {"3", "XM,NOURISH,GOVERNMENT", ",,CIVILIZATION"},
            //#2
            {"2", "ADVANCE,ENLIGHTENED_A", ",ENLIGHTENMENT"},
            {"2", "ATTACK,EVOLUTION", null},
            {"2", "AVOID,CONFLICT", null},
            {"2", "CAPTURE,OPENING", ",PORTAL"},
            {"2", "CHANGE,PRESENT", null},
            {"2", "ESCAPE,TOGETHER", null},
            {"2", "GOVERNMENT,CHAOS", "CIVILIZATION,"},
            {"2", "GOVERNMENT,WEAK", "CIVILIZATION,"},
            {"2", "CREATE,DANGER", null},
            {"2", "CREATE,FUTURE", null},
            {"2", "DEFEND,NATURE", null},
            {"2", "DIFFICULT,BARRIER", null},
            {"2", "DISCOVER,ENLIGHTENED_A", ",ENLIGHTENMENT"},
            {"2", "DISCOVER,LIE", null},
            {"2", "DISCOVER,OPENING", ",PORTAL"},
            {"2", "DISCOVER,RESISTANCE_A", ",RESISTANCE"},
            {"2", "ESCAPE,EVOLUTION", null},
            {"2", "FOLLOW,JOURNEY", null},
            {"2", "GAIN,HARMONY", ",PEACE"},
            {"2", "GAIN,SAFETY", null},
            {"2", "HIDE,TRUTH", null},
            {"2", "IMPROVE,BEING", ",HUMAN"},
            {"2", "JOURNEY,NO", ",INSIDE"},
            {"2", "LEAD,RESISTANCE_A", ",RESISTANCE"},
            {"2", "LIBERATE,XM", null},
            {"2", "NOURISH,JOURNEY", null},
            {"2", "OPEN ALL,OPENING", ",PORTAL"},
            {"2", "OPEN ALL,TRUTH", null},
            {"2", "PATH,HARMONY", ",PEACE"},
            {"2", "PATH,PERFECTION", null},
            {"2", "HARMONY,STABILITY", "PEACE,"},
            {"2", "PURE,CHAOS", null},
            {"2", "PURE,LIE", null},
            {"2", "PURE,MIND", null},
            {"2", "PURE,SHAPERS", null},
            {"2", "PURE,TRUTH", null},
            {"2", "PURSUE,CONFLICT", null},
            {"2", "PURSUE,JOURNEY", null},
            {"2", "QUESTION,ALL", null},
            {"2", "QUESTION,GOVERNMENT", ",CIVILIZATION"},
            {"2", "QUESTION,TRUTH", null},
            {"2", "QUESTION,ATTACK", ",WAR"},
            {"2", "RETREAT,SAFETY", null},
            {"2", "SEE,SHAPERS", null},
            {"2", "SEE,TRUTH", null},
            {"2", "SEEK,POTENTIAL", null},
            {"2", "SEPARATE,ATTACK", ",WAR"},
            {"2", "STRONG,BODY", null},
            {"2", "STRONG,MIND", null},
            {"2", "STRONG,SOUL", null},
    };

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 + "(" +
                "id integer primary key autoincrement," +
                "name text not null," +
                "path text not null);");
        db.execSQL("create table " + TABLE_NAME2 + "(" +
                "id integer primary key autoincrement," +
                "level integer not null," +
                "sequence text not null," +
                "correctSeq text);");

        db.beginTransaction();
        try {
            SQLiteStatement stmt;
            
            stmt = db.compileStatement("insert into shapers(name, path) values(?, ?);");
            for (String[] shaper : SHAPERS) {
                stmt.bindString(1, shaper[0]);
                stmt.bindString(2, shaper[1]);
                stmt.executeInsert();
            }
            stmt = db.compileStatement("insert into sets(level, sequence, correctSeq) values(?, ?, ?);");
            for (String[] set : SETS) {
                stmt.bindString(1, set[0]);
                stmt.bindString(2, set[1]);
                if (set[2] != null) {
                    stmt.bindString(3, set[2]);
                } else {
                    stmt.bindNull(3);
                }
                stmt.executeInsert();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE shapers;");
        db.execSQL("DROP TABLE sets;");
        onCreate(db);
    }
}


