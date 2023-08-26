README.notification-service.md

ElectronicDiary(Электронный Дневник).

Сервис для отправки электронных сообщений.

Автор: Alexey Kozlov:)

Open Insomnia and create a new POST request to URL: http://localhost:7070/send-email 

Headers: Content-Type     application/json

Request body as JSON:

{
"parentEmail": "parent@example.com",
"subject": "Student Grade",
"templateName": "grade_email_template",
"context": {
"lesson": "Math",
"grade": "A+"
}
}

Sendinblue

electronicdiary24@gmail.com   d-nSQ8t5V!GvXgf

Master Password   6zN7mjDJkInSO4UK
