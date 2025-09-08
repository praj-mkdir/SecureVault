resource "aws_sqs_queue_policy" "allow_s3" {
  queue_url = var.queue_url

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = { Service = "s3.amazonaws.com" }
        Action    = "SQS:SendMessage"
        Resource  = var.queue_arn
        Condition = {
          ArnEquals = {
            "aws:SourceArn" : var.bucket_arn
          }
        }
      }
    ]
  })
}

resource "aws_s3_bucket_notification" "to_sqs" {
  bucket = var.bucket_name

  queue {
    queue_arn = var.queue_arn
    events    = ["s3:ObjectCreated:*"]
  }

  depends_on = [aws_sqs_queue_policy.allow_s3]
}