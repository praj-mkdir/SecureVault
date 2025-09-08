provider "aws" {
  region = var.region
}

module "s3_bucket" {
  source      = "../../modules/s3"
  bucket_name = "securevaultprajv1"
  environment = "prod"
}

module "sqs" {
  source     = "../../modules/sqs"
  queue_name = "s3eventsQ"
}

module "s3_to_sqs" {
  source     = "../../modules/s3_to_sqs"
  bucket_name  = module.s3_bucket.bucket_name
  bucket_arn = module.s3_bucket.bucket_arn
  queue_url  = module.sqs.queue_url
  queue_arn  = module.sqs.queue_arn
}

