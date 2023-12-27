<img src="http://img.netbout.com/logo.svg" width="132px"/>

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/netbout)](http://www.rultor.com/p/yegor256/netbout)
[![We recommend RubyMine](https://www.elegantobjects.org/rubymine.svg)](https://www.jetbrains.com/ruby/)

[![PDD status](http://www.0pdd.com/svg?name=yegor256/netbout)](http://www.0pdd.com/p?name=yegor256/netbout)
[![Hits-of-Code](https://hitsofcode.com/github/yegor256/netbout)](https://hitsofcode.com/view/github/yegor256/netbout)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/netbout/blob/master/LICENSE.txt)
[![Availability at SixNines](https://www.sixnines.io/b/6fb0)](https://www.sixnines.io/h/6fb0)

Netbout.com is a communication platform that enables smoothless integration
of humans and software agents in a conversation-centered environment.

The original idea behind Netbout is explained in USPTO patent application [US 12/943,022](https://www.google.com/patents/US20120117164).

## Functionality

A user can (both via web interface and RESTful JSON API):
 
  * Login via GitHub
  * Create a unique identifier
  * Start a bout with a title
  * Join a bout
  * Post a `VARCHAR(65536)` message to a bout
  * Assign an immutable `VARCHAR(256)` variable to a bout
  * List all visible bouts (with pagination) by search string
  * List all messages by bout-id (with pagination) and search string
  * Read message content by bout-id/message-id
  * Read variable content by bout-id/variable-name

While listing bouts and messages, a search string may be used, which 
is similar to what GitHub uses for searches:

  * "Hello, world!" --- the text must be in the body of a message
  * `author:yegor256` --- the author must be `yegor256`
  * `before:2023-12-14` --- posted before 14-Dec-23
  * `after:2023-12-14` --- posted after 14-Dec-23

Predicates may be groupped using `or`, `and`, and brackets, for example:

```
"important" and (author:yegor256 or (after:2023-12-14 and "something" and "Hello"))
```

## How to test?

If you're a manual tester and want to contribute to a project, please
login to Netbout.com, create an account and do whatever you think is reasonable
to reveal [functional](http://en.wikipedia.org/wiki/Functional_requirement)
and [non-functional](http://en.wikipedia.org/wiki/Non-functional_requirement)
problems in the system. Each bug you
find, report in [a new Github issue](https://github.com/yegor256/netbout/issues/new).

Please, read these articles before starting to test and report bugs:
[Five Principles of Bug Tracking](http://www.yegor256.com/2014/11/24/principles-of-bug-tracking.html),
[Bugs Are Welcome](http://www.yegor256.com/2014/04/13/bugs-are-welcome.html),
and
[Wikipedia's Definition of a Software Bug Is Wrong](http://www.yegor256.com/2015/06/11/wikipedia-bug-definition.html).

## How to contribute?

If you're a software developer and want to contribute to
the project, please fork the repository, make changes, and submit a pull request.
We promise to review your changes same day and apply to
the `master` branch, if they look correct.

Please run Maven (3.1 or higher!) build before submitting a pull request:

```
$ mvn clean install -Pqulice
```

If your default encoding is not UTF-8, some of unit tests will break. This is an intentional behavior. To fix that, set this environment variable in console (in Windows, for example):

```
SET JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
```

Similarly, on Linux:

```
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
```

## Run locally and test it

Want to run it locally? Simple as that:

```
$ mvn clean install -Phit-refresh -Dport=8080
```

In a minute the site is ready at `http://localhost:8080`

In case you are looking for an even quicker startup or experience failing tests,
but still want to run the site, you can skip the tests by running:

```
$ mvn clean install -DskipTests -Phit-refresh -Dport=8080
```

## Integration tests

It is highly recommended to run integration tests to guarantee that your changes will not break any other part of the system.
Follow these steps to execute integration tests:

1. Open your browser's console and go to network view;
1. Access the netbout server you want to test with and log in (if you are not). You may access production site [http://www.netbout.com/](http://www.netbout.com/) or your local snapshot [http://localhost:8080](http://localhost:8080);
1. On network monitor of your browser, select the connection that requested main page www.netbout.com.
1. On that request, search for the response headers.
1. You will find a key named `Set-Cookie` on that response.
1. The value of that header, contains a lot of other `key values` content. Search for content of `PsCookie` variable and copy that content.
1. Go back to you console and run the following code, replacing the `${cookie_value}` for the content that you copied from `PsCookie`, replacing the `${netbout_server}` for the site that you accessed:

  ```
  $ mvn clean install -Dnetbout.token=${cookie_value} -Dnetbout.url=${netbout_server}
  ```
