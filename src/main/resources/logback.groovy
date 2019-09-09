import java.nio.charset.Charset

// Message
String ansiPattern = '%gray([%10.10thread]) ' + // Thread
                     '%highlight(%-5level) ' + // Log level
                     '%cyan(%-40.40logger{39}) %gray(:) ' + // Logger
                     '%m%n'
// Message

String nonAnsiPattern = '%d{ISO8601} [%10.10thread] %-5level %-40.40logger{39} : %msg%n'


// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')
        pattern = ansiPattern
    }
}

root(INFO, ['STDOUT'])