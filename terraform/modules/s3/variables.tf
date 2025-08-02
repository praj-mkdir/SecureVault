variable "bucket_name" {
  description = "Name of the S3 bucket"
  type        = string
}

variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "environment" {
  description = "Environment (e.g., dev, prod)"
  type        = string
  default     = "dev"
}
